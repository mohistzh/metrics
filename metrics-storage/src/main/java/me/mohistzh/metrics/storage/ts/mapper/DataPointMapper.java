package me.mohistzh.metrics.storage.ts.mapper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.DataPoint;
import me.mohistzh.metrics.model.pojo.MetricsInstance;
import org.apache.commons.collections4.MapUtils;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.impl.TimeUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TS data object manipulation
 * @Author Jonathan
 * @Date 2019/12/10
 **/
@Data
@Slf4j
public class DataPointMapper {

    private InfluxDB influxDB;
    private String database;

    /**
     * Default write data point func
     * @param dataPoints
     */
    public void write(List<DataPoint> dataPoints) {
        BatchPoints batchPoints =  BatchPoints.database(database).build();
        for (DataPoint dataPoint : dataPoints) {
            if (dataPoint == null) {
                continue;
            }
            if (dataPoint.getValue() == null) {
                log.warn("ignored empty value data-point: " + dataPoint);
                continue;
            }

            Point.Builder builder = Point.measurement(dataPoint.getName());
            if (MapUtils.isNotEmpty(dataPoint.getTags())) {
                builder.tag(dataPoint.getTags());
            }
            Point point = builder.addField("value", dataPoint.getValue()).time(dataPoint.getTimestamp(), TimeUnit.MILLISECONDS).build();
            batchPoints.point(point);
        }
        influxDB.write(batchPoints);
    }

    /**
     * Write data-point by given instance
     * @param dataPoints
     * @param metricsInstance
     */
    public void writeByMetricsInstance(List<DataPoint> dataPoints, MetricsInstance metricsInstance) {
        BatchPoints batchPoints = BatchPoints.database(database).build();
        for (DataPoint dataPoint : dataPoints) {

            if (dataPoint == null) {
                continue;
            }
            Point.Builder builder = Point.measurement(dataPoint.getName());
            if (MapUtils.isNotEmpty(dataPoint.getTags())) {
                builder.tag(dataPoint.getTags());
            }
            Point point = builder.tag("host", metricsInstance.getHost()).
                    tag("port", String.valueOf(metricsInstance.getPort())).
                    tag("type", metricsInstance.getSourceType().toString()).
                    addField("value", dataPoint.getValue()).
                    time(dataPoint.getTimestamp(), TimeUnit.MILLISECONDS).build();

            batchPoints.point(point);
        }
        influxDB.write(batchPoints);

    }

}
