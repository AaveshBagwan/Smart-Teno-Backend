package com.platform.common.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class GenericEnumSerializer<E extends Enum<E>> extends JsonSerializer<E> {

    @Override
    public void serialize(E e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (e == null) {
            jsonGenerator.writeNull();
            return;
        }
        try {
            jsonGenerator.writeString(e.name().replace("_"," "));
        } catch (Exception ex) {
            log.error("Error serializing enum {}: {}", e.getClass().getSimpleName(), ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

}
