package com.today.kafka.monitor;

import com.today.kafka.monitor.msg.KafkaMsgProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author hz.lei
 */
@SpringBootApplication
@EnableConfigurationProperties(KafkaMsgProperties.class)
@Slf4j
public class KafkaMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaMonitorApplication.class, args);
    }
}
