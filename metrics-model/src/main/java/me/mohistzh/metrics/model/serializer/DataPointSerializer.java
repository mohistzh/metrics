package me.mohistzh.metrics.model.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AtomicDouble;
import me.mohistzh.metrics.model.pojo.DataPoint;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author Jonathan
 * @Date 2019/12/13
 **/
public class DataPointSerializer implements Deserializer<DataPoint> {


    private String encoding = "UTF8";

    public static AtomicDouble samplingRate = new AtomicDouble(0.001);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String propertyName = isKey ? "key.deserializer.encoding" : "value.deserializer.encoding";
        Object encodingValue = configs.get(propertyName);
        if (encodingValue == null) {
            encodingValue = configs.get("deserializer.encoding");
        }

        if (encodingValue != null && encodingValue instanceof String) {
            this.encoding =(String) encodingValue;
        }
    }

    @Override
    public DataPoint deserialize(String s, byte[] bytes) {
        try {
            String rawMsg = new String(bytes, this.encoding);
            DataPoint dataPoint = new DataPoint();
            JSONObject jsonObject = JSON.parseObject(rawMsg);

            dataPoint.setName(jsonObject.getString("key"));
            long timestamp = jsonObject.getLong("timestamp");
            if ((timestamp / 1000000000L) < 10) {
                timestamp = timestamp * 1000;
            }
            dataPoint.setTimestamp(timestamp);
            if (jsonObject.get("value") instanceof Number) {
                if (dataPoint.getName().endsWith(".bytes")) {
                    dataPoint.setValue((long) Math.floor(jsonObject.getDouble("value")));
                } else {
                    dataPoint.setValue(jsonObject.getDouble("value"));
                }
            } else {
                dataPoint.setValue(0);
            }

            JSONObject tagJson = jsonObject.getJSONObject("tags");
            Map<String, String> tags = Maps.newTreeMap();
            tagJson.entrySet().stream().filter(e -> e.getValue() != null && !StringUtils.isEmpty(String.valueOf(e.getValue()))).forEach(
                    e -> tags.put(e.getKey(), String.valueOf(e.getValue())));

            dataPoint.setTags(tags);
            return dataPoint;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }
}
