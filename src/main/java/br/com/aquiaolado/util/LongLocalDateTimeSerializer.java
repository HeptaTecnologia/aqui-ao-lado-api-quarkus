package br.com.aquiaolado.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

public class LongLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jg,
                          SerializerProvider sp) throws IOException {
        Long longData = dateTime.toInstant(UTC).toEpochMilli();
        jg.writeNumber(longData);
    }
}

