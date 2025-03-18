package com.flinksoftware.demo.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        final String sanitizedBigDecimalString;

        final String bigDecimalString = parser.getText();
        if (bigDecimalString.contains(",")) {
            sanitizedBigDecimalString = bigDecimalString.replace(",", ".");
        } else {
            sanitizedBigDecimalString = bigDecimalString;
        }

        return new BigDecimal(sanitizedBigDecimalString);
    }
}
