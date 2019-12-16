package me.mohistzh.metrics.consumer.api;

import me.mohistzh.metrics.model.dto.DataResult;
import me.mohistzh.metrics.model.serializer.DataPointSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Jonathan
 * @Date 2019/12/16
 **/
@RequestMapping("/v1")
@RestController
public class KafkaConsumerController {

    @RequestMapping(value = "/sr", method = RequestMethod.GET)
    public DataResult<Double> getSamplingRate() {
        return DataResult.success(DataPointSerializer.samplingRate.get());
    }

    @RequestMapping(value = "/sr", method = RequestMethod.PUT)
    public DataResult<Double> modifySamplingRate(@RequestParam("sr") Double sr) {
        double oldOne = DataPointSerializer.samplingRate.get();
        DataPointSerializer.samplingRate.set(sr);
        return DataResult.success(oldOne);
    }
}
