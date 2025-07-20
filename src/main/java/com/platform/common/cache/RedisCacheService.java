package com.platform.common.cache;

import com.platform.common.model.redis.RedisKeys;
import com.platform.common.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisCacheService {

    private final RedisTemplate<String, String> redisTemplate;

    public <T> void save(T value, RedisKeys<T> redisKey, String...keySuffix) {
        String key = redisKey.info().key(keySuffix);
        if(value == null){
            throw new IllegalArgumentException("Value cannot be null");
        }
        if (StringUtils.hasText(key)) {
            this.redisTemplate.opsForValue().set(key, JsonUtils.serialize(value));
        }
    }

    public <T> void save(T value, long ttlInSec, RedisKeys<T> redisKey, String...keySuffix) {
        String key = redisKey.info().key(keySuffix);
        if(value == null){
            throw new IllegalArgumentException("Value cannot be null");
        }
        if (StringUtils.hasText(key)) {
            this.redisTemplate.opsForValue().set(key, JsonUtils.serialize(value), ttlInSec);
        }
    }

    public <T> T load(RedisKeys<T> redisKey, String...keySuffix) {
        return Optional.of(redisKey)
                .map(RedisKeys::info)
                .map(keyInfo -> keyInfo.key(keySuffix))
                .map(key -> this.redisTemplate.opsForValue().get(key))
                .map(value -> JsonUtils.deserialize(value, redisKey.info().valueType()))
                .orElse(null);
    }

    public <T> void remove(RedisKeys<T> redisKey, String...keySuffix) {
        String key = redisKey.info().key(keySuffix);
        if(StringUtils.hasText(key)) {
            this.redisTemplate.delete(key);
        }
    }

}
