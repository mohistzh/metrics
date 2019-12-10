package me.mohistzh.metrics.transactor.rd;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.MetricsInstance;
import me.mohistzh.metrics.storage.rd.mapper.MetricsInstanceMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author Jonathan
 * @Date 2019/12/10
 **/
@Slf4j
@Service
public class MetricsInstanceService {

    @Autowired
    private MetricsInstanceMapper metricsInstanceMapper;

    private LoadingCache<Integer, MetricsInstance> instanceLoadingCache = CacheBuilder.newBuilder().build(
            new CacheLoader<Integer, MetricsInstance>() {
                @Override
                public MetricsInstance load(Integer key) throws Exception {
                    MetricsInstance instance = metricsInstanceMapper.getMetricsInstancesById(key);
                    return instance;
                }
            }
    );

    /**
     * Insert into RD then cache on local
     * @param instance
     * @return
     */
    public boolean addMetricsInstance(MetricsInstance instance) {
        List<MetricsInstance> instanceList = metricsInstanceMapper.getInstancesByHostAndPort(instance.getHost(), instance.getPort());
        if (CollectionUtils.isNotEmpty(instanceList)) {
            return false;
        }
        metricsInstanceMapper.insertMetricsInstances(Arrays.asList(instance));
        instanceLoadingCache.put(instance.getId(), instance);
        return true;
    }

    /**
     * Get Data Model from Cache
     * @param id
     * @return
     */
    public MetricsInstance getMetricsInstanceById(int id) {
        MetricsInstance metricsInstance = null;
        try {
            metricsInstance = instanceLoadingCache.get(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error("Fetching MetricsInstance occurs exception: " + id);
        }
        return metricsInstance;
    }

    /**
     * Delete cache first then reload new data from RD
     */
    @PostConstruct
    public synchronized void refreshLoadingCache() {
        instanceLoadingCache.invalidateAll();
        List<MetricsInstance> metricsInstances = metricsInstanceMapper.getAllInstances();
        metricsInstances.forEach(ele -> instanceLoadingCache.put(ele.getId(), ele));
    }

    public List<MetricsInstance> loadAllInstances() {
        return metricsInstanceMapper.getAllInstances();
    }
}
