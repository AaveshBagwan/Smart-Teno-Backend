package com.platform.common.config.attributeconverter;

import com.platform.common.constants.StatusEnum;

public class EnumAttributeConverters {

    public static class StatusConverter extends GenericEnumToDatabaseAttributeConverter<StatusEnum> {
        public StatusConverter() {
            super(StatusEnum.class);
        }
    }

}
