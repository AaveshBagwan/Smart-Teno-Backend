package com.platform.common.model.redis;

public interface KeyInfo<T> {

    String key(String[] keySuffix);

    Class<T> valueType();

}
