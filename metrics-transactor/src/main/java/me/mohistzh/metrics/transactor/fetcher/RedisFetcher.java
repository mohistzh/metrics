package me.mohistzh.metrics.transactor.fetcher;

import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.MetricsInstance;
import me.mohistzh.metrics.transactor.feed.RedisFeedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @Author Jonathan
 * @Date 2019/12/20
 **/
@Slf4j
@Component
public class RedisFetcher implements BasicFetcher<String>{

    @Autowired
    private RedisFeedManager redisFeedManager;

    @Override
    public String fetch(MetricsInstance metricsInstance) {
        Jedis jedis = redisFeedManager.getMetricsConnector(metricsInstance);
        if (jedis == null) {
            return null;
        }
        String[] parts = metricsInstance.getMetricsAPI().split(" ");
        String section = parts.length == 1 ? "default" : parts[1];

        return jedis.info(section);
    }
}
