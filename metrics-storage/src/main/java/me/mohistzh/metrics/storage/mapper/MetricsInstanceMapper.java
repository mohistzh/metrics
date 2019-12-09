package me.mohistzh.metrics.storage.mapper;

import me.mohistzh.metrics.model.pojo.MetricsInstance;
import java.util.List;

public interface MetricsInstanceMapper {

    int insertMetricsInstances(List<MetricsInstance> instances);

    List<MetricsInstance> getAllInstances();

    MetricsInstance getMetricsInstancesById(int id);
}
