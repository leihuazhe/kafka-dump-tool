package com.today.kafka.monitor.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Desc: kafka consumer registry
 *
 * @author hz.lei
 * @date 2018年05月17日 下午9:12
 */
@Component
public class KafkaMonitorRegistry {

    @Autowired
    private KafkaMsgProperties msgProperties;

    private Map<String, MsgConsumer> consumerMap = new ConcurrentHashMap<>();

    public void registerConsumer(String topic) {
        KafkaMonitorConsumer consumer = new KafkaMonitorConsumer();

    }

    public void startConsumer(String topic){

    }


}
