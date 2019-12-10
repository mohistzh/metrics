package me.mohistzh.metrics.transactor.parser;

import me.mohistzh.metrics.model.pojo.DataPoint;
import java.util.List;

public interface BasicDataParser<T> {
    List<DataPoint> parse(T input);
}
