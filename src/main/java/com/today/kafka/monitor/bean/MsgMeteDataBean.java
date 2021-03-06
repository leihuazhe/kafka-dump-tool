package com.today.kafka.monitor.bean;

import com.github.dapeng.openapi.cache.ZkBootstrap;
import com.today.kafka.monitor.msg.KafkaMsgProperties;
import com.today.kafka.monitor.xml.EventDataHolder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Desc: MsgMeteDataBean
 *
 * @author hz.lei
 * @date 2018年05月17日 下午3:07
 */
@Component
@Slf4j
public class MsgMeteDataBean implements InitializingBean {

    private final KafkaMsgProperties msgProperties;

    public MsgMeteDataBean(KafkaMsgProperties msgProperties) {
        this.msgProperties = msgProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (System.getenv(msgProperties.ENV_SOA_ZOOKEEPER_HOST) != null
                || System.getProperty(msgProperties.PROP_SOA_ZOOKEEPER_HOST) != null) {
            log.info("zk host in the environment is already setter...");
        } else {
            System.setProperty(msgProperties.PROP_SOA_ZOOKEEPER_HOST, msgProperties.getZkHost());
            log.info("zk host in the environment is not found,setting it with spring boot application, host is {}", msgProperties.getZkHost());
        }
        ZkBootstrap bootstrap = new ZkBootstrap();
        Set<String> serviceSet;
        //可进行精确指定
        if (msgProperties.getAlias() != null) {
            serviceSet = EventDataHolder.getServiceSetByAlias(msgProperties.getAlias());
        } else {
            serviceSet = EventDataHolder.getServiceSet();
        }
        bootstrap.init();
//        bootstrap.filterInit(serviceSet);
    }
}
