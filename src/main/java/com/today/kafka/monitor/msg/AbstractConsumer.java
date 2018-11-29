package com.today.kafka.monitor.msg;

import com.today.kafka.monitor.service.DumpConfig;
import com.today.kafka.monitor.socketio.SocketIoServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
 * @since 2018-11-21 1:34 PM
 */
@Slf4j
public abstract class AbstractConsumer {
    protected AtomicInteger counter = new AtomicInteger(0);
    protected final DumpConfig config;
    protected final SocketIoServer ioServer;

    public AbstractConsumer(DumpConfig config, SocketIoServer ioServer) {
        this.config = config;
        this.ioServer = ioServer;
    }

    /**
     * 配置
     */
    protected Properties configConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", config.getKafkaHost());
        props.put("group.id", config.getKey());
        //no commit to broker, unnecessary
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "10000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        return props;
    }

    public abstract void start();
}
