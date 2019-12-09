package me.mohistzh.metrics.model.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Each metrics data formed by MetricsDataPoint
 *
 * @Author Jonathan
 * @Date 2019/12/9
 **/
@Data
public class MetricsDataPoint {
    private String name;
    private String value;
    private Date timestamp;
}
