package com.today.kafka.monitor.xml;

import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Desc: event 事件元信息持有者
 *
 * @author hz.lei
 * @date 2018年05月17日 上午12:08
 */
public class EventDataHolder {


    private static Map<String, Consumers.Consumer> serviceMap = new HashMap<>(64);


    private static Logger logger = LoggerFactory.getLogger(EventDataHolder.class);

    private static Consumers consumerEvent = parseXmlData();

    /**
     * parse xml
     */
    public static Consumers parseXmlData() {
        Persister persister = new Persister();
        Consumers config = null;
        try {
            ClassPathResource resource = new ClassPathResource("consumer-event.xml");
            InputStream inputStream = resource.getInputStream();

            config = persister.read(Consumers.class,
                    inputStream);
        } catch (FileNotFoundException e) {
            logger.error("配置文件 consumer-event.xml 在classpath路径下不存在，请进行配置", e);
            throw new RuntimeException("配置文件 consumer-event.xml 在classpath路径下不存在，请进行配置");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Assert.notNull(config, "Endpoint must be set");

        return config;
    }

    public static Map<String, Consumers.Consumer> getEventMap() {
        Map<String, Consumers.Consumer> eventMap = new HashMap<>(64);
        consumerEvent.getConsumers().forEach(consumer -> eventMap.put(consumer.getEventType(), consumer));
        return Collections.unmodifiableMap(eventMap);
    }


    public static Set<String> getServiceSet() {
        Set<String> servicesSet = new HashSet<>(64);
        consumerEvent.getConsumers().forEach(consumer -> servicesSet.add(consumer.getService()));
        return Collections.unmodifiableSet(servicesSet);
    }

    /**
     * 根据指定的topic 别名获取指定服务元信息
     *
     * @param alias
     * @return
     */
    public static Set<String> getServiceSetByAlias(String alias) {
        Set<String> services = consumerEvent.getConsumers()
                .stream()
                .filter(consumer -> consumer.getId().equals(alias))
                .map(consumer -> consumer.getService())
                .collect(Collectors.toSet());

        return Collections.unmodifiableSet(services);
    }
}
