package me.mohistzh.metrics.api;

import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.dto.DataResult;
import me.mohistzh.metrics.model.pojo.MetricsInstance;
import me.mohistzh.metrics.transactor.feed.BasicFeedManager;
import me.mohistzh.metrics.transactor.rd.MetricsInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RESTFul API service of Metrics Instance
 * @Author Jonathan
 * @Date 2019/12/11
 **/
@Slf4j
@RestController
@RequestMapping("/v1")
public class MetricsInstanceContoller {

    @Autowired
    MetricsInstanceService instanceService;

    @Autowired
    List<BasicFeedManager> feedManagers;

    /**
     * Create a metrics instance
     * @param metricsInstance
     * @return
     */
    @RequestMapping(value = "instances", method = RequestMethod.POST)
    public DataResult<MetricsInstance> addInstance(MetricsInstance metricsInstance) {
        boolean added = instanceService.addMetricsInstance(metricsInstance);
        return added ? DataResult.success() : DataResult.success(0, "Duplicated instance", null);
    }

    /**
     * List all instances
     * @return
     */
    @RequestMapping(value = "instances", method = RequestMethod.GET)
    public DataResult<List<MetricsInstance>> listInstances() {
        List<MetricsInstance> instances = instanceService.loadAllInstances();
        instances.forEach(ele -> ele.setPassword("******"));
        return DataResult.success(instances);
    }

    /**
     * Refresh cache
     * @return
     */
    @RequestMapping(value = "instances/cache/refresh", method = RequestMethod.GET)
    public DataResult<String> refreshCache() {
        instanceService.refreshLoadingCache();
        return DataResult.success();
    }

    /**
     * Refresh feed streams.
     * @return
     */
    @RequestMapping(value = "instances/feeds/refresh", method = RequestMethod.GET)
    public DataResult<String> refreshFeed() {
        feedManagers.stream().forEach(e -> e.refresh());
        return DataResult.success();
    }




}
