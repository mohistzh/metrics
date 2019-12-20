package me.mohistzh.metrics.consumer;

import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.DataPoint;
import me.mohistzh.metrics.model.serializer.DataPointSerializer;
import me.mohistzh.metrics.storage.ts.mapper.DataPointMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
/**
 *
 * @Author Jonathan
 * @Date 2019/12/13
 **/
@Slf4j
public abstract class AbstractConsumer {

    /**
     * Get server name
     * @return
     */
    abstract public String getServer();

    /**
     * Get group name
     *
     * @return
     */
    abstract public String getGroup();

    /**
     * Get topic name
     * @return
     */
    abstract public String getTopic();

    /**
     * Get amount of threads
     * @return
     */
    abstract public int getCountOfThreads();

    abstract public DataPointMapper getDataPointMapper();


    /**
     * Infinite loops solution to listen kafka data stream.
     */
    protected void startListener() {
        Runnable task = () -> {
            /**
             * initialize configuration
             */
            final Properties properties = new Properties();
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getServer());
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, getGroup());
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DataPointSerializer.class.getName());

            /**
             * subscribe kafka topic
             */
            Consumer consumer = new KafkaConsumer(properties);
            consumer.subscribe(Collections.singletonList(getTopic()));

            while (true) {
                long start = System.currentTimeMillis();
                // to get data per a second
                ConsumerRecords<Long, DataPoint> consumerRecords = consumer.poll(Duration.ofMillis(1000));
                if (consumerRecords.count() == 0) {
                    continue;
                }
                List<DataPoint> dataPointList = new ArrayList<>();
                consumerRecords.forEach(e -> dataPointList.add(e.value()));

                getDataPointMapper().write(dataPointList);
                consumer.commitAsync();
                long end = System.currentTimeMillis();
                log.debug("Inserted " + dataPointList.size() + " records into InfluxDB cost "+(end - start) +"ms");
             }
        };

        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadFactory threadFactory = r -> {
            Thread t = new Thread(r, "kafka-consumer-"+atomicInteger.getAndIncrement());
            return t;
        };

        ExecutorService pool = Executors.newFixedThreadPool(getCountOfThreads(), threadFactory);
        for (int i = 0; i < getCountOfThreads(); i++) {
            pool.execute(task);
        }

    }

}
