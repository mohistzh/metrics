package me.mohistzh.metrics.transactor.feed;

import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.MetricsInstance;

import java.util.concurrent.ExecutionException;

/**
 * Shared function
 * @Author Jonathan
 * @Date 2019/12/10
 **/
@Slf4j
public abstract class AbstractFeedManager<T> implements BasicFeedManager<T>{
    protected abstract LoadingCache<MetricsInstance, T> getCache();

    @Override
    public T getMetricsConnector(MetricsInstance metricsInstance) {
        T t = null;
        try {
            t = getCache().get(metricsInstance);
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error("Connect to instance occurs exception: "+ metricsInstance, e);
        }
        return t;
    }
}
