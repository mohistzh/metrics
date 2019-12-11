package me.mohistzh.metrics.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main
 * @Author Jonathan
 * @Date 2019/12/11
 **/
@SpringBootApplication(scanBasePackages = "me.mohistzh.metrics")
public class MetricsEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetricsEngineApplication.class);
    }
}
