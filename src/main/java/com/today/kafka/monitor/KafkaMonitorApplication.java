package com.today.kafka.monitor;

import com.today.kafka.monitor.msg.KafkaMonitorConsumer;
import com.today.kafka.monitor.msg.KafkaMsgProperties;
import com.today.kafka.monitor.xml.EventDataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
public class KafkaMonitorApplication implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private KafkaMonitorConsumer monitor;

    public static void main(String[] args) {
        SpringApplication.run(KafkaMonitorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //初始化 map
        EventDataHolder.parseXmlData();
        ExecutorService service = Executors.newFixedThreadPool(1);
        logger.info("\n--------------------------------------------start consumer -----------------------------------\n");
        service.execute(() -> monitor.start());
    }
}
