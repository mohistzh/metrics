package me.mohistzh.metrics.transactor.feed;

import me.mohistzh.metrics.model.pojo.MetricsInstance;

public interface BasicFeedManager<T> {
    T getMetricsConnector(MetricsInstance metricsInstance);
    void refresh();
}
