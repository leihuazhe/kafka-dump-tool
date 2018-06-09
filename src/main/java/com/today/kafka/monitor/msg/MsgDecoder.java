package com.today.kafka.monitor.msg;

import com.github.dapeng.core.metadata.Service;
import com.github.dapeng.json.JsonSerializer;
import com.github.dapeng.openapi.cache.ServiceCache;
import com.github.dapeng.org.apache.thrift.TException;
import com.github.dapeng.org.apache.thrift.protocol.TCompactProtocol;
import com.github.dapeng.util.MetaDataUtil;
import com.github.dapeng.util.TCommonTransport;
import com.github.dapeng.util.TKafkaTransport;
import com.today.eventbus.serializer.KafkaMessageProcessor;
import com.today.kafka.monitor.xml.Consumers;
import com.today.kafka.monitor.xml.EventDataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Desc: MsgDecoder
 *
 * @author hz.lei
 * @date 2018年05月16日 下午10:12
 */
public class MsgDecoder {
    private static Logger logger = LoggerFactory.getLogger(MsgDecoder.class);

    /**
     * 将事件解码为 json 形式
     *
     * @param value
     * @return
     * @throws TException
     */
    protected static String dealMessage(byte[] value) throws TException {
        KafkaMessageProcessor processor = new KafkaMessageProcessor();
        String eventType;
        try {
            eventType = processor.getEventType(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("[RestKafkaConsumer]:解析消息eventType出错，忽略该消息");
            return null;
        }
        //根据eventType 获取到在xml中定义的事件元信息
        Consumers.Consumer bizConsumer = EventDataHolder.getEventMap().get(eventType);

        if (bizConsumer == null) {
            logger.warn("");
            return null;
        }
        //通过事件元信息，通过请求thrift元数据得到事件序列化结构体
        Service service = ServiceCache.getService(bizConsumer.getService(), bizConsumer.getVersion());

        /*if (service == null) {
            int i = 0;
            while (service == null && i < 3) {
                service = ServiceCache.getService(bizConsumer.getService(), bizConsumer.getVersion());
                if (service != null) {
                    break;
                }
                i++;
                try {
                    Thread.sleep(i * 1000);
                } catch (InterruptedException e) {
                }
            }
        }*/

        if (service == null) {
            throw new RuntimeException("获取不到service cache 元信息");
        }

        byte[] eventBinary = processor.getEventBinary();
        /**
         * 针对 dapeng 2.0.2
         */
        JsonSerializer jsonDecoder = new JsonSerializer(service, null, bizConsumer.getVersion(), MetaDataUtil.findStruct(bizConsumer.getEvent(), service));

        String body = jsonDecoder.read(new TCompactProtocol(new TKafkaTransport(eventBinary, TCommonTransport.Type.Read)));


        return body;

    }
}
