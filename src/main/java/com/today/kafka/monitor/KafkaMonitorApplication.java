package com.today.kafka.monitor;

import com.today.kafka.monitor.msg.KafkaMonitorConsumer;
import com.today.kafka.monitor.msg.KafkaMsgProperties;
import com.today.kafka.monitor.xml.EventDataHolder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hz.lei
 */
@SpringBootApplication
@EnableConfigurationProperties(KafkaMsgProperties.class)
@Slf4j
public class KafkaMonitorApplication implements ApplicationRunner {
    private final KafkaMonitorConsumer monitor;

    public KafkaMonitorApplication(KafkaMonitorConsumer monitor) {
        this.monitor = monitor;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaMonitorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化 map
        EventDataHolder.parseXmlData();
        ExecutorService service = Executors.newFixedThreadPool(1);
        log.info("\n--------------------------------------------start consumer -----------------------------------\n");
        service.execute(monitor::start);
    }
}
