package me.mohistzh.metrics.model.pojo;

import com.google.common.collect.ImmutableMap;
import lombok.Data;

import java.util.Map;

/**
 * Abstract object of all kind of metrics data point.
 * @Author Jonathan
 * @Date 2019/12/10
 **/
@Data
public class DataPoint {

    private String name;
    private Number value;
    private Long timestamp;
    private Map<String, String> tags = ImmutableMap.of();
}
