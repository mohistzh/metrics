package me.mohistzh.metrics.transactor.parser;

import com.google.common.collect.Lists;
import me.mohistzh.metrics.model.pojo.CommonMetricsData;
import me.mohistzh.metrics.model.pojo.DataPoint;

import java.util.List;

/**
 * Parsing mysql data stream
 * @Author Jonathan
 * @Date 2019/12/10
 **/
public class MySQLRowDataParser implements BasicDataParser<List<CommonMetricsData>> {
    @Override
    public List<DataPoint> parse(List<CommonMetricsData> input) {

        List<DataPoint> dataPoints = Lists.newArrayList();
        if (input == null) {
            return dataPoints;
        }
        for (CommonMetricsData entry : input) {
            if (!isNumberic(entry.getValue())) {
                continue;
            }
            DataPoint dataPoint = new DataPoint();
            dataPoint.setName("mysql." + entry.getVariable().toLowerCase() + ".counter");
            dataPoint.setTimestamp(System.currentTimeMillis());
            dataPoints.add(dataPoint);
        }
        return dataPoints;
    }

    private boolean isNumberic(final String input) {
        if (input == null || "".equals(input)) {
            return false;
        }
        return input.chars().allMatch(Character::isDigit);
    }
}
