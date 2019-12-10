package me.mohistzh.metrics.storage.ts.config;

import me.mohistzh.metrics.storage.ts.mapper.DataPointMapper;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author Jonathan
 * @Date 2019/12/10
 **/
@Component
@Configuration
public class InfluxDBConfig {
    @Bean
    public InfluxDB influxDB(@Value("${influx.url}") String url,
                             @Value("${influx.username}") String username,
                             @Value("${influx.password}") String password) {
        return InfluxDBFactory.connect(url, username, password);
    }

    @Bean("coreInflux")
    public DataPointMapper coreInflux(@Value("${influx.url}") String url,
                                      @Value("${influx.username}") String username,
                                      @Value("${influx.password}") String password,
                                      @Value("${influx.database}") String database) {
        DataPointMapper dataPointMapper = new DataPointMapper();
        dataPointMapper.setInfluxDB(InfluxDBFactory.connect(url, username, password));
        dataPointMapper.setDatabase(database);
        return dataPointMapper;
    }


}
