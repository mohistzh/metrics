package me.mohistzh.metrics.transactor.parser;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.DataPoint;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author Jonathan
 * @Date 2019/12/20
 **/
@Slf4j
@Component
public class RedisRawRawDataParser implements BasicRawDataParser<String> {

    private static final String PREFIX = "redis.";
    private static final String SUFFIX = ".counter";


    @Override
    public List<DataPoint> parse(String input) {
        long timestamp = System.currentTimeMillis();
        List<DataPoint> dataPoints = Lists.newArrayList();
        if (input == null) {
            return dataPoints;
        }
        String[] lines = input.split(System.lineSeparator());
        String category = null;
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            if (line.startsWith("#")) {
                category = line.substring(1).trim();
                continue;
            }
            String[] kv = line.split(":");
            if (kv.length != 2) {
                log.debug("Not standard line: " + line);
            }

            String name = kv[0].trim();
            String value = kv[1].trim();

            if (NumberUtils.isNumber(value)) {
                Number numValue = NumberUtils.createNumber(value);
                DataPoint dataPoint = new DataPoint();
                dataPoint.setTimestamp(timestamp);
                dataPoint.setName(PREFIX + name + SUFFIX);
                dataPoint.setValue(numValue);
                dataPoint.setTags(ImmutableMap.of("category", category));
                dataPoints.add(dataPoint);
            } else if ("Commandstats".equals(category)) {
                String[] nameParts = name.split("_");
                String[] valueParts = value.split(",");
                for (String valuePart : valueParts) {
                    String[] valueDetail = valuePart.split("=");
                    DataPoint dataPoint = new DataPoint();
                    dataPoint.setTimestamp(timestamp);
                    dataPoint.setName(PREFIX + nameParts[0] + "_" + valueDetail[0] + SUFFIX);
                    dataPoint.setValue(NumberUtils.createNumber(valueDetail[1]));
                    dataPoint.setTags(ImmutableMap.of("category", category, "command", nameParts[1]));
                    dataPoints.add(dataPoint);
                }
            } else if ("Keyspace".equals(category)) {
                String[] valueParts = value.split(",");
                for (String valuePart : valueParts) {
                    String[] valueDetail = valuePart.split("=");
                    DataPoint dataPoint = new DataPoint();
                    dataPoint.setTimestamp(timestamp);
                    dataPoint.setName(PREFIX + name + "_" + valueDetail[0] + SUFFIX);
                    dataPoint.setValue(NumberUtils.createNumber(valueDetail[1]));
                    dataPoint.setTags(ImmutableMap.of("category", category));
                    dataPoints.add(dataPoint);
                }
            } else {
                continue;
            }

        }
        return dataPoints;
    }
}
