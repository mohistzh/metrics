package me.mohistzh.metrics.transactor.fetcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.MetricsInstance;
import me.mohistzh.metrics.transactor.feed.RabbitMQFeedManager;
import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ Queue metrics, refer <a>https://www.rabbitmq.com/monitoring.html#queue-metrics</a>
 *
 * @Author Jonathan
 * @Date 2019/12/23
 **/
@Slf4j
@Component
public class RabbitMQQueueFetcher implements BasicFetcher<JSONArray>{

    @Autowired
    private RabbitMQFeedManager rabbitMQFeedManager;

    @Override
    public JSONArray fetch(MetricsInstance metricsInstance) {
        Executor executor = Executor.newInstance().auth(new HttpHost(metricsInstance.getHost(), metricsInstance.getPort()),
                metricsInstance.getUsername(), metricsInstance.getPassword());

        Request request = rabbitMQFeedManager.getMetricsConnector(metricsInstance);
        try {
            String content = executor.execute(request).returnContent().asString();
            JSONArray jsonArray = JSON.parseArray(content);
            return jsonArray;
        } catch (Exception e) {
            log.error("Failed to retrieve rabbitmq vhost metrics." ,e);
        }
        return null;
    }
}
