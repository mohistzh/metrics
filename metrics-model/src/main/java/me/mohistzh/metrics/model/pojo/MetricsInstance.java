package me.mohistzh.metrics.model.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author Jonathan
 * @Date 2019/12/9
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"type", "host", "port", "username", "password", "metricsAPI"})
public class MetricsInstance {
    private int id;
    private MetricsSourceTypeEnum sourceType;
    public MetricsInstance(int id) {
        this.id = id;
    }
    @NotNull(message = "host can not be empty")
    private String host;
    private int port;
    private String username;
    private String password;
    private String metricsAPI;
    private Date createdAt;
    private Date updatedAt;

}
