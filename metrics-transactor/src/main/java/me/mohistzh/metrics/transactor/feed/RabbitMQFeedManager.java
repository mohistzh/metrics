package me.mohistzh.metrics.transactor.feed;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.MetricsInstance;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * A rabbitmq feed stream manager
 * @Author Jonathan
 * @Date 2019/12/23
 **/
@Slf4j
@Component
public class RabbitMQFeedManager extends AbstractFeedManager<Request> {

    private static final String SCHEMA = "http";

    /**
     * caching RabbitMQ configuration info for 1 hour
     */
    private LoadingCache<MetricsInstance, Request> requestLoadingCache = CacheBuilder.newBuilder().expireAfterAccess(60, TimeUnit.MINUTES).
            build(new CacheLoader<MetricsInstance, Request>() {
                @Override
                public Request load(MetricsInstance instance) throws Exception {
                    URL url = new URL(SCHEMA, instance.getHost(), instance.getPort(), instance.getMetricsAPI());
                    return Request.Get(url.toString());
                }
            });

    @Override
    protected LoadingCache<MetricsInstance, Request> getCache() {
        return this.requestLoadingCache;
    }

    @Override
    public void refresh() {
        this.requestLoadingCache.invalidateAll();
    }
}
