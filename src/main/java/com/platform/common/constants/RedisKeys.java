package com.platform.common.constants;

import com.platform.common.utils.CommonUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.function.Supplier;

public enum RedisKeys {
    ACCESS_TOKEN(() -> CommonUtils.getProperty("redis.key-prefix.access-token")),
    REFRESH_TOKEN(() -> CommonUtils.getProperty("redis.key-prefix.refresh-token")),;

    private final Supplier<String> keyPrefix;
    private String keyPrefixCache = null;

    private String getKeyPrefix() {
        if(StringUtils.hasText(keyPrefixCache)) {
            return keyPrefixCache;
        }
        return keyPrefixCache = this.keyPrefix.get().toLowerCase();
    }

    private RedisKeys(Supplier<String> keyPrefix){
        this.keyPrefix=keyPrefix;
    }

    public String getKey(String...keySuffixes) {
        StringBuilder key = new StringBuilder(getKeyPrefix());
        Arrays.stream(keySuffixes).forEach(keySuffix ->{
            if(StringUtils.hasText(keySuffix)){
                key.append(":").append(keySuffix);
            }
        });
        return key.toString();
    }
}
