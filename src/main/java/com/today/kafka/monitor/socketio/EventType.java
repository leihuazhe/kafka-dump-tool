package com.today.kafka.monitor.socketio;

import lombok.Getter;

/**
 * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
 * @since 2018-11-20 4:45 PM
 */
@Getter
public enum EventType {
    DATA_INSERT("DATA_INSERT"),
    MESSAGE("MESSAGE");

    private String name;

    EventType(String name) {
        this.name = name;
    }
}
