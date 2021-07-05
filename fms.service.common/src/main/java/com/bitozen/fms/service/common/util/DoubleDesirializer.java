package com.bitozen.fms.service.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;

/**
 *
 * @author Reza Agika Putra
 */
public class DoubleDesirializer extends JsonSerializer<Double> {

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {

        BigDecimal d = new BigDecimal(value);
        gen.writeNumber(d.toPlainString());

    }

}
