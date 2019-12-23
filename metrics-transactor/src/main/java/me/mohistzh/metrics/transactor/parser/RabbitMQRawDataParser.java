package me.mohistzh.metrics.transactor.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import me.mohistzh.metrics.model.pojo.DataPoint;
import me.mohistzh.metrics.model.pojo.RabbitMQRawMetrics;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Parsing rabbitmq data stream
 * @Author Jonathan
 * @Date 2019/12/23
 **/
@Component
public class RabbitMQRawDataParser implements BasicRawDataParser<RabbitMQRawMetrics>{

    private static final String PREFIX = "rabbitmq.";
    private static final String SUFFIX = ".counter";

    @Override
    public List<DataPoint> parse(RabbitMQRawMetrics input) {
        List<DataPoint> result = Lists.newArrayList();
        if (input == null) {
            return result;
        }

        JSONObject jsonObject = (JSONObject) JSON.toJSON(input);

        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {

            String value = String.valueOf(entry.getValue());
            if (!NumberUtils.isNumber(value)) {
                continue;
            }
            DataPoint dataPoint = new DataPoint();
            dataPoint.setName(PREFIX + entry.getKey() + SUFFIX);
            dataPoint.setValue(NumberUtils.createNumber(value));
            dataPoint.setTimestamp(System.currentTimeMillis());
            result.add(dataPoint);
        }
        return result;
    }
}
