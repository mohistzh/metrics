package me.mohistzh.metrics.model.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Author Jonathan
 * @Date 2019/12/9
 **/
@Data
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
