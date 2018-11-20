package com.today.kafka.monitor.controller;

import com.today.kafka.monitor.msg.KafkaMonitorRegistry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc: TopicController
 *
 * @author hz.lei
 * @date 2018年05月17日 下午9:08
 */
@RestController
public class TopicController {
    private final KafkaMonitorRegistry monitorRegistry;

    public TopicController(KafkaMonitorRegistry monitorRegistry) {
        this.monitorRegistry = monitorRegistry;
    }


    @RequestMapping("/topic/{topic}")
    public String getTopicMsg(@PathVariable(name = "topic") String topic) {
        monitorRegistry.registerConsumer(topic);
        monitorRegistry.startConsumer(topic);
        return "";
    }

    @RequestMapping("/stop")
    public String stopCurrentTopicConsumer(String topic) {


        return "";
    }


}
