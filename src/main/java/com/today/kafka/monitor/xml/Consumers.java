package com.today.kafka.monitor.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * 描述: kafka消息代理 配置 根目录
 *
 * @author hz.lei
 * @date 2018年05月03日 上午12:45
 */
@Root(name = "consumers")
public class Consumers {

    @ElementList(name = "consumer", type = Consumer.class, inline = true)
    private List<Consumer> consumers;

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<Consumer> consumers) {
        this.consumers = consumers;
    }


    @Root(name = "consumer")
    public static class Consumer {

        @Element(required = false)
        private String id;


        @Element
        private String eventType;


        @Element
        private String service;

        @Element
        private String version;

        @Element
        private String event;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }
    }
}
