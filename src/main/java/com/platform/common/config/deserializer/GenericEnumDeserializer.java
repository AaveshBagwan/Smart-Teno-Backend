package com.platform.common.config.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.platform.common.exception.InvalidEnumValueFromJsonException;
import org.apache.coyote.BadRequestException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;

public class GenericEnumDeserializer extends JsonDeserializer<Enum<?>> implements ContextualDeserializer {

    private final Class<? extends Enum<?>> enumClass;

    public GenericEnumDeserializer() {
        this.enumClass = null;
    }

    public GenericEnumDeserializer(Class<? extends Enum<?>> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        Class<?> rawClass = property.getType().getRawClass();
        if (rawClass.isEnum()) {
            return new GenericEnumDeserializer((Class<? extends Enum<?>>) rawClass);
        }
        return this;
    }

    @Override
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (enumClass == null) {
            throw new IllegalStateException("Enum class is not set");
        }
        if(!StringUtils.hasText(p.getValueAsString())){
            throw new BadRequestException(enumClass.getSimpleName()+" cannot be null or blank");
        }
        String value = p.getValueAsString().replace(" ","_");
        for (Enum<?> constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(value)) {
                return constant;
            }
        }
        throw new InvalidEnumValueFromJsonException(
                String.format("Invalid value '%s' for enum '%s', Valid values are %s", value, enumClass.getSimpleName(),
                        Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).toList())
        );
    }

}
