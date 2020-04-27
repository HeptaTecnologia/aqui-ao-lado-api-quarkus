package br.com.aquiaolado.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LongLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jp,
                                     DeserializationContext dc) throws IOException {
        ObjectCodec codec = jp.getCodec();
        TextNode node = new TextNode(codec.readTree(jp).toString());
        String dateString = node.textValue();
        Instant instant = Instant.ofEpochMilli(Long.parseLong(dateString));
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        return dateTime;
    }
}
