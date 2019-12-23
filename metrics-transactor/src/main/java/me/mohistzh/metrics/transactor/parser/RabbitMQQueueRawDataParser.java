package me.mohistzh.metrics.transactor.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.DataPoint;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Parsing rabbitmq queue metrics
 * @Author Jonathan
 * @Date 2019/12/23
 **/
@Slf4j
@Component
public class RabbitMQQueueRawDataParser implements BasicRawDataParser<JSONArray> {

    private static final String PREFIX = "rabbitmq_queue.";
    private static final String SUFFIX = ".counter";


    @Override
    public List<DataPoint> parse(JSONArray input) {
        List<DataPoint> result = Lists.newArrayList();
        for (int i = 0; i < input.size(); i++) {
            List<DataPoint> dataPoints = Lists.newArrayList();
            JSONObject vhostData = input.getJSONObject(i);
            String queueName = vhostData.getString("name");
            String vhostName = vhostData.getString("vhost");

            composeJSONObject(vhostData, dataPoints, null);
            dataPoints.stream().filter(e -> e != null).forEach(
                e -> e.setTags(ImmutableMap.of(
                        "name", queueName,
                        "vhost", vhostName
                )
            ));

            result.addAll(dataPoints);
        }
        return result;
    }
    private void composeJSONObject(JSONObject jsonObject, List<DataPoint> dataPoints, String key) {
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String newKey = key == null ? entry.getKey() : key + "." + entry.getKey();
            if (entry.getValue() instanceof JSONObject) {
                composeJSONObject(jsonObject.getJSONObject(entry.getKey()), dataPoints, newKey);
            } else if (entry.getValue() instanceof JSONArray) {
                componseDataPointList(jsonObject.getJSONArray(entry.getKey()), dataPoints, newKey);
            } else {
                dataPoints.add(createDataPoint(PREFIX + newKey + SUFFIX, entry.getValue()));
            }
        }
    }

    /**
     * Generate a new DataPoint object with name and value
     * @param name
     * @param value
     * @return
     */
    private DataPoint createDataPoint(String name, Object value) {
        String valueString = String.valueOf(value);
        if (!NumberUtils.isNumber(valueString)) {
            return null;
        }
        DataPoint dataPoint = new DataPoint();
        dataPoint.setName(name);
        dataPoint.setValue(NumberUtils.createNumber(valueString));
        dataPoint.setTimestamp(System.currentTimeMillis());
        return dataPoint;
    }

    /**
     * Componse Data Point List from given json array
     * @param jsonArray
     * @param dataPoints
     * @param key
     */
    private void componseDataPointList(JSONArray jsonArray, List<DataPoint> dataPoints, String key) {
        for (int i = 0; i < jsonArray.size(); i++) {
            Object value = jsonArray.get(i);
            String newKey = key + "." + i;
            if (value instanceof JSONObject) {
                composeJSONObject(jsonArray.getJSONObject(i), dataPoints, newKey);
            } else if (value instanceof JSONArray) {
                componseDataPointList(jsonArray.getJSONArray(i), dataPoints, newKey);
            } else {
                String valueString = String.valueOf(value);
                if (!NumberUtils.isNumber(valueString)) {
                    continue;
                }
                DataPoint dataPoint = new DataPoint();
                dataPoint.setName(PREFIX + newKey + SUFFIX);
                dataPoint.setValue(NumberUtils.createNumber(valueString));
                dataPoint.setTimestamp(System.currentTimeMillis());
                dataPoints.add(dataPoint);
            }
        }
    }
}
