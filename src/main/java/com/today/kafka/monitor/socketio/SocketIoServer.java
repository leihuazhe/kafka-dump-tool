package com.today.kafka.monitor.socketio;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SocketIoServer {
    private static final String host = "127.0.0.1";
    private static final int port = 9001;

    private SocketIOServer server;

    public SocketIOServer getServer() {
        return server;
    }

    @PostConstruct
    private void init() {
        Configuration config = new Configuration();
        config.setPort(port);
        config.setHostname(host);
        config.setAllowCustomRequests(true);

        Map<String, Object> nodesMap = new ConcurrentHashMap<>();
        Map<String, Object> webClientMap = new ConcurrentHashMap<>();

        server = new SocketIOServer(config);

        server.addConnectListener(socketIOClient -> {
            log.info(String.format("客户端: %s 加入房间 %s ", socketIOClient.getRemoteAddress(), socketIOClient.getSessionId()));
        });

        server.addDisconnectListener(socketIOClient -> {
            if (nodesMap.containsKey(socketIOClient.getSessionId().toString())) {
                socketIOClient.leaveRoom("nodes");
                nodesMap.remove(socketIOClient.getSessionId().toString());

                log.info(String.format("leave room  nodes %s", socketIOClient.getSessionId()));
            }

            if (webClientMap.containsKey(socketIOClient.getSessionId().toString())) {
                socketIOClient.leaveRoom("web");
                log.info(String.format("leave room web  %s", socketIOClient.getSessionId()));
                webClientMap.remove(socketIOClient.getSessionId().toString());
                // web 离开通知所有agent客户端

               /* nodesMap.values().forEach(agent -> {
                    SocketIOClient targetAgent = server.getClient(UUID.fromString(agent.getSessionId()));
                    if (targetAgent != null) {
                        targetAgent.sendEvent(EventType.WEB_LEAVE().name(), EventType.WEB_LEAVE().name());
                    }
                });*/
            }
        });

        server.addEventListener(EventType.DATA_INSERT.getName(), String.class,
                (client, data, ackRequest) -> {
                    client.joinRoom("web");
                    log.info("web Reg..." + client.getSessionId());

                    String s = client.getRemoteAddress().toString();
                    String remoteIp = s.replaceFirst("/", "").substring(0, s.lastIndexOf(":") - 1);
                    String name = data.split(":")[0];
                    String ip = data.split(":")[1];
                }

        );
        server.start();
        log.info("======> SOCKET IO 启动了 .....websocket server started at " + port);
    }

    @PreDestroy
    public void destroy() {
        server.stop();
    }
}
