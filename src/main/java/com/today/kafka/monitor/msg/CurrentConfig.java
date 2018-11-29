package com.today.kafka.monitor.msg;

import com.github.dapeng.core.metadata.Struct;
import com.github.dapeng.json.OptimizedMetadata;
import lombok.Data;

/**
 * @author <a href=mailto:leihuazhe@gmail.com>maple</a>
 * @since 2018-11-22 11:42 AM
 */
@Data
public class CurrentConfig {
    private String event;

    private String eventType;

    private OptimizedMetadata.OptimizedStruct eventStruct;

    private OptimizedMetadata.OptimizedService service;

    private String version;


    public CurrentConfig(String event, String eventType, OptimizedMetadata.OptimizedStruct eventStruct, OptimizedMetadata.OptimizedService service, String version) {
        this.event = event;
        this.eventType = eventType;
        this.eventStruct = eventStruct;
        this.service = service;
        this.version = version;
    }
}
