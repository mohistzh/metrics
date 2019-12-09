package me.mohistzh.metrics.model.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Defined an abstract Metrics Instance Data Model.
 * @Author Jonathan
 * @Date 2019/12/9
 **/
@Data
@NoArgsConstructor
public class MetricsInstance {
    private int id;
    private String type;
    private String urlConnection;
    private String username;
    private String password;
    private String metricsAPI;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;
}
