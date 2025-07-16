package com.platform.common.config.attributeconverter;

import com.platform.common.exception.InvalidEnumValueFromDbException;
import jakarta.persistence.AttributeConverter;

/**
 * A generic attribute converter for converting enum values to database strings and vice versa.
 * This converter replaces underscores with spaces when converting to the database and
 * replaces spaces with underscores when converting from the database.
 *
 * @param <E> the type of the enum
 */
public class GenericEnumToDatabaseAttributeConverter<E extends Enum<E>> implements AttributeConverter<E, String> {

    private final Class<E> enumClass;

    public GenericEnumToDatabaseAttributeConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute != null ? attribute.name().replace("_", " ") : null;
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return Enum.valueOf(enumClass, dbData.replace(" ", "_"));
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumValueFromDbException("Invalid enum value: " + dbData + " for enum " + enumClass.getSimpleName());
        }
    }

}

