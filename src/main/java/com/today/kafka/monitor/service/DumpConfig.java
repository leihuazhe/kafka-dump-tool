package com.today.kafka.monitor.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
 * @since 2018-11-20 4:30 PM
 */
@Slf4j
@Data
public class DumpConfig {
    /**
     * key
     */
    private String key;
    /**
     * zookeeper host
     */
    private String zookeeperHost;
    /**
     * kafka host
     */
    private String kafkaHost;
    /**
     * group id
     */
    private String groupId;
    /**
     * subscribe topic
     */
    private String topic;
    /**
     * message which partition
     */
    private long partition;
    /**
     * topic message offset
     */
    private long begin;
    /**
     * message size
     */
    private long limit;

}
