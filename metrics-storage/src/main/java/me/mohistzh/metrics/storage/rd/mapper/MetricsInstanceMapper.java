package me.mohistzh.metrics.storage.rd.mapper;

import me.mohistzh.metrics.model.pojo.MetricsInstance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MetricsInstanceMapper {

    int insertMetricsInstances(List<MetricsInstance> instances);

    List<MetricsInstance> getAllInstances();

    List<MetricsInstance> getInstancesByHostAndPort(@Param("host") String host, @Param("port") int port);

    MetricsInstance getMetricsInstancesById(int id);
}
