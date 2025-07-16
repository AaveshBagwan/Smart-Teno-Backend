package com.platform.common.cache;

import com.platform.common.utils.JsonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class LocalCacheService {

    private final Map<String, String> cacheMap = new ConcurrentHashMap<>();

    public void save(String key, Object value) {
        if (StringUtils.hasText(key) && Objects.nonNull(value)) {
            cacheMap.put(key, JsonUtils.serialize(value));
        }
    }

    public String load(String key) {
        if (StringUtils.hasText(key)) {
            return cacheMap.get(key);
        }
        return null;
    }

    public void remove(String key) {
        if (StringUtils.hasText(key)) {
            cacheMap.remove(key);
        }
    }

}
