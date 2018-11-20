package com.today.kafka.monitor.msg;

import com.today.kafka.monitor.service.DumpConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Desc: KafkaMonitorConsumer
 *
 * @author hz.lei
 * @date 2018年05月16日 下午9:38
 * @update 2018.11.20 17:43
 */
@Slf4j
public class KafkaDumpConsumer {
    private AtomicInteger counter = new AtomicInteger(0);
    private final DumpConfig config;

    public KafkaDumpConsumer(DumpConfig config) {
        this.config = config;
    }

    /**
     * 配置
     *
     * @return properties
     */
    private Properties configConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", config.getKafkaHost());
        props.put("group.id", config.getGroupId());
        //no commit to broker, unnecessary
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "10000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        return props;
    }

    public void start() {
        Properties props = configConsumer();
        //设置如何把byte转成object类型，例子中，通过指定string解析器，我们告诉获取到的消息的key和value只是简单个string类型。
        final KafkaConsumer<Long, byte[]> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(config.getTopic()), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                partitions.forEach(p -> consumer.seek(p, config.getBegin()));
            }
        });
        log.info("start to analyze event,groupId:{},topic:{},begin offset:{},limit:{}",
                config.getGroupId(), config.getTopic(), config.getBegin(), config.getLimit());

        loop:
        while (true) {
            ConsumerRecords<Long, byte[]> records = consumer.poll(100);
            for (ConsumerRecord<Long, byte[]> record : records) {
                if (counter.incrementAndGet() <= config.getLimit()) {
                    String json = null;
                    try {
                        json = MsgDecoder.dealMessage(record.value());
                    } catch (Exception e) {
                        log.info("consumer fetch message failed when dump some message,cause:{}", e.getMessage());
                    }
                    if (json == null) {
                        json = new String(record.value(), StandardCharsets.UTF_8);
                    }
                    log.info("receive: partition:{}, offset:{}, topic:{}, value:{}\n\n",
                            record.partition(), record.offset(), record.topic(), json);
                } else {
                    break loop;
                }
            }
        }
    }
}
