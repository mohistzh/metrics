package me.mohistzh.metrics.transactor.fetcher;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.MetricsInstance;
import me.mohistzh.metrics.model.pojo.RabbitMQRawMetrics;
import me.mohistzh.metrics.transactor.feed.RabbitMQFeedManager;
import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author Jonathan
 * @Date 2019/12/23
 **/
@Slf4j
@Component
public class RabbitMQFetcher implements BasicFetcher<RabbitMQRawMetrics>{

    @Autowired
    private RabbitMQFeedManager rabbitMQFeedManager;


    @Override
    public RabbitMQRawMetrics fetch(MetricsInstance metricsInstance) {
        Executor executor = Executor.newInstance().auth(new HttpHost(metricsInstance.getHost(), metricsInstance.getPort()),
                            metricsInstance.getUsername(), metricsInstance.getPassword());

        Request request = rabbitMQFeedManager.getMetricsConnector(metricsInstance);
        try {
            String content = executor.execute(request).returnContent().asString();
            return JSON.parseObject(content, RabbitMQRawMetrics.class);
        } catch (Exception e) {
            log.error("Failed to retrieve RabbitMQ metrics.", e);
        }

        return null;
    }
}
