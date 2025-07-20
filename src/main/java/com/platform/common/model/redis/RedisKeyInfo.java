package com.platform.common.model.redis;

import com.platform.common.utils.ContextUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.function.Supplier;

public class RedisKeyInfo<T> implements KeyInfo<T> {

    private final Supplier<String> keyPrefix;
    private String keyPrefixCache = null;

    private final Class<T> valueType;

    private RedisKeyInfo(Supplier<String> keyPrefix, Class<T> valueType) {
        this.keyPrefix = keyPrefix;
        this.valueType = valueType;
    }

    public static <K> RedisKeyInfo<K> of(String property, Class<K> valueType) {
        return new RedisKeyInfo<>(() -> ContextUtils.getProperty(property), valueType);
    }

    private String getKeyPrefix() {
        if (StringUtils.hasText(keyPrefixCache)) {
            return keyPrefixCache;
        }
        return keyPrefixCache = this.keyPrefix.get().toLowerCase();
    }

    @Override
    public String key(String...keySuffixes) {
        StringBuilder key = new StringBuilder(getKeyPrefix());
        Arrays.stream(keySuffixes).forEach(keySuffix ->{
            if(StringUtils.hasText(keySuffix)){
                key.append(":").append(keySuffix);
            }
        });
        return key.toString();
    }

    @Override
    public Class<T> valueType() {
        return this.valueType;
    }

}
