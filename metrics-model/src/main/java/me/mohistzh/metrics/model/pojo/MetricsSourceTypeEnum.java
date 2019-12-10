package me.mohistzh.metrics.model.pojo;

/**
 * Enum of Metrics instances
 */
public enum MetricsSourceTypeEnum {
    MYSQL(""),
    REDIS(""),
    RABBITMQ("");

    protected String fetcherClass;

    MetricsSourceTypeEnum(String className) {
        this.fetcherClass = className;
    }

}
