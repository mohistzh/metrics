package me.mohistzh.metrics.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Jonathan
 * @Date 2019/12/10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonMetricsData {
    private String variable;
    private String value;
}
