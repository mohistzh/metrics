package me.mohistzh.metrics.consumer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.storage.ts.mapper.DataPointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author Jonathan
 * @Date 2019/12/16
 **/
@Data
@Slf4j
@Component
@EqualsAndHashCode(callSuper = false)
public class MetricsKafkaConsumer extends AbstractConsumer {

    @Value("${kafka.server}")
    private String server;

    @Value("${kafka.group}")
    private String group;

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    @Qualifier("coreInflux")
    private DataPointMapper dataPointMapper;

    @Value("${consumer.threads}")
    private int countOfThreads = 1;

    @EventListener
    public void startListener(ContextRefreshedEvent event) {
        super.startListener();
    }

}
