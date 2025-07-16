package com.platform.common.constants;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.platform.common.config.deserializer.GenericEnumDeserializer;
import com.platform.common.config.serializer.GenericEnumSerializer;

@JsonSerialize(using = GenericEnumSerializer.class)
@JsonDeserialize(using = GenericEnumDeserializer.class)
public enum StatusEnum {
    A,I;
}
