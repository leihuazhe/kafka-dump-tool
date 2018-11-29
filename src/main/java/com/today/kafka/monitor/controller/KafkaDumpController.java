package com.today.kafka.monitor.controller;

import com.today.kafka.monitor.msg.KafkaDumpConsumer;
import com.today.kafka.monitor.service.KafkaMonitorRegistry;
import com.today.kafka.monitor.service.DumpConfig;
import com.today.kafka.monitor.socketio.SocketIoServer;
import com.today.kafka.monitor.util.CommonUtil;
import com.today.kafka.monitor.util.Resp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
 * @since 2018-11-20 5:50 PM
 */
@RestController
@RequestMapping("/admin")
public class KafkaDumpController {
    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    private final SocketIoServer ioServer;

    public KafkaDumpController(SocketIoServer ioServer) {
        this.ioServer = ioServer;
    }

    @PostMapping("/listExistConsumer")
    public Object listExistConsumer() {
        Map<String, KafkaDumpConsumer> consumerMap = KafkaMonitorRegistry.getConsumerMap();
        return Resp.success(consumerMap);
    }

    @PostMapping("/createConsumer")
    public Object dumpEvent(@RequestBody DumpConfig dumpConfig) {
        String key = dumpConfig.getKey();
        KafkaMonitorRegistry.registerConsumer(key, new KafkaDumpConsumer(dumpConfig, ioServer));
        return Resp.success(key);
    }


    @PostMapping("/startConsumer")
    public Object startConsumer(@RequestBody Map<String, String> params) {
        String consumerKey = CommonUtil.notNullRet(params.get("key"), "consumerKey could not be null...");
        KafkaDumpConsumer dumpConsumer = KafkaMonitorRegistry.getConsumer(consumerKey);
        if (dumpConsumer != null) {
            executorService.execute(dumpConsumer::start);
        }
        return Resp.success();
    }

}
