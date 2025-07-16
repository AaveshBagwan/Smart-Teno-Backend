package com.platform.common.config.converter;

import com.platform.common.exception.InternalServerException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * A generic converter that converts a String to an Enum of type T.
 * It replaces spaces, dashes, and dots with underscores, and converts the string to uppercase
 * before attempting to convert it to the specified Enum type.
 * while also handling camelCase to snake_case conversion.
 * This is useful for converting properties from configuration files to Enum values.
 */
public class GenericPropsToEnumConverter<T extends Enum<T>> implements Converter<String, T> {

    private final Class<T> enumClass;

    public GenericPropsToEnumConverter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public T convert(String source) {
        if(!StringUtils.hasText(source)){
            return null;
        }

        String enumName = source
                .replace(" ", "_")
                .replace("-", "_")
                .replace(".", "_")
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toUpperCase();

        try {
            return Enum.valueOf(this.enumClass, enumName);
        } catch (Exception e) {
            throw new InternalServerException("Invalid enum value: " + source + " for enum " + this.enumClass.getSimpleName(), e);
        }
    }

}
