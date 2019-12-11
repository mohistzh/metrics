package me.mohistzh.metrics.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main
 * @Author Jonathan
 * @Date 2019/12/11
 **/
@MapperScan("me.mohistzh.metrics.storage.rd.mapper")
@SpringBootApplication(scanBasePackages = "me.mohistzh.metrics")
public class MetricsEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetricsEngineApplication.class);
    }
}
