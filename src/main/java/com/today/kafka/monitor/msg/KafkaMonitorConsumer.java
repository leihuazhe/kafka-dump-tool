package com.today.kafka.monitor.msg;

import com.github.dapeng.org.apache.thrift.TException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * Desc: KafkaMonitorConsumer
 *
 * @author hz.lei
 * @date 2018年05月16日 下午9:38
 */
@Component
@Slf4j
public class KafkaMonitorConsumer {
    private boolean flag = false;

    private final KafkaMsgProperties msgEnv;

    public KafkaMonitorConsumer(KafkaMsgProperties msgEnv) {
        this.msgEnv = msgEnv;
    }

    public void start() {
        Properties props = new Properties();
        props.put("bootstrap.servers", msgEnv.getHost());
        props.put("group.id", "kafka_monitor");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "10000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

        //设置如何把byte转成object类型，例子中，通过指定string解析器，我们告诉获取到的消息的key和value只是简单个string类型。
        final KafkaConsumer<Long, byte[]> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList(msgEnv.getTopic()), new ConsumerRebalanceListener() {

            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

            }

            /**
             * consumer.seekToBeginning(partitions);
             * @param partitions
             */
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                partitions.forEach(p -> {
                    consumer.seek(p, msgEnv.getOffset());
                });
            }
        });

        flag = true;
        log.info("start to analyze event...");

        while (flag) {
            ConsumerRecords<Long, byte[]> records = consumer.poll(100);
            for (ConsumerRecord<Long, byte[]> record : records) {
                try {
                    String json = null;
                    try {
                        json = MsgDecoder.dealMessage(record.value());
                    } catch (NullPointerException e) {
                        log.info(e.getMessage(), e);
                    }
                    if (json == null) {
                        try {
                            json = new String(record.value(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            log.error("[UnsupportedEncodingException]:json为空，编码消息出错," + e.getMessage());
                        }
                    }
                    log.info("receive: partition:{}, offset:{}, topic:{}, value:{}\n\n", record.partition(), record.offset(), record.topic(), json);
                } catch (TException e) {
                    log.error("[TException]:解析消息出错," + e.getMessage());
                }

            }
        }
    }


    public void stop() {
        flag = false;
    }
}
