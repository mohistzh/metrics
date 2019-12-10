package me.mohistzh.metrics.transactor.fetcher;

import me.mohistzh.metrics.model.pojo.MetricsInstance;

public interface BasicFetcher<T> {
    T fetch(MetricsInstance metricsInstance);
}

