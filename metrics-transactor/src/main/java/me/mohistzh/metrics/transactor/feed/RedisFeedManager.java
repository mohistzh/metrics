package me.mohistzh.metrics.transactor.feed;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.MetricsInstance;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * We use Jedis as redis client
 * @Author Jonathan
 * @Date 2019/12/20
 **/
@Slf4j
@Component
public class RedisFeedManager extends AbstractFeedManager<Jedis>{

    // caching Jedis instance for 15min
    private LoadingCache<MetricsInstance, Jedis> jedisLoadingCache = CacheBuilder.newBuilder()
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .build(new CacheLoader<MetricsInstance, Jedis>() {
                @Override
                public Jedis load(MetricsInstance key) throws Exception {
                    Jedis jedis = new Jedis(key.getHost(), key.getPort());
                    if (StringUtils.hasText(key.getPassword())) {
                        jedis.auth(key.getPassword());
                    }
                    return jedis;
                }
            });

    @Override
    protected LoadingCache getCache() {
        return jedisLoadingCache;
    }

    @Override
    public void refresh() {
        jedisLoadingCache.invalidateAll();
    }
}
