package com.today.kafka.monitor.service;

import com.today.kafka.monitor.msg.KafkaDumpConsumer;
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
    private static Map<String, KafkaDumpConsumer> consumerMap = new ConcurrentHashMap<>();

    public static KafkaDumpConsumer registerConsumer(String key, KafkaDumpConsumer dumpConsumer) {
        consumerMap.put(key, dumpConsumer);
        return dumpConsumer;
    }

    public static Map<String, KafkaDumpConsumer> getConsumerMap() {
        return consumerMap;
    }

    public static KafkaDumpConsumer getConsumer(String key) {
       return consumerMap.get(key);

    }

    public static void stopConsumer(String key) {
        KafkaDumpConsumer kafkaDumpConsumer = consumerMap.get(key);
    }

    public static void removeConsumer(String key) {
        consumerMap.remove(key);
    }


}
