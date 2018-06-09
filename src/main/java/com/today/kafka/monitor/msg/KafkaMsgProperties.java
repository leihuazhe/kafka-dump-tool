package com.today.kafka.monitor.msg;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc: KafkaMsgProperties
 *
 * @author hz.lei
 * @date 2018年05月16日 下午11:27
 */
@ConfigurationProperties(prefix = "kafka")
public class KafkaMsgProperties {

    private String topic;

    private String host;

    private String zkHost;

    private long offset;

    private String alias;

    public final String ENV_SOA_ZOOKEEPER_HOST = "soa_zookeeper_host";

    public final String PROP_SOA_ZOOKEEPER_HOST = "soa.zookeeper.host";

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String getZkHost() {
        return zkHost;
    }

    public void setZkHost(String zkHost) {
        this.zkHost = zkHost;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
