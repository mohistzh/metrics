package me.mohistzh.metrics.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Jonathan
 * @Date 2019/12/16
 **/
@SpringBootApplication(scanBasePackages = "me.mohistzh.metrics")
public class KafkaConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class);
    }
}
