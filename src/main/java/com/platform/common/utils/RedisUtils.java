package com.platform.common.utils;

import com.platform.common.cache.RedisCacheService;
import com.platform.common.model.redis.RedisKeys;

public class RedisUtils {

    public static <T> void save(T value, RedisKeys<T> redisKey, String... keySuffix) {
        RedisCacheService redisCacheService = ContextUtils.getBean(RedisCacheService.class);
        redisCacheService.save(value, redisKey, keySuffix);
    }

    public static <T> void save(T value, long ttlInSec, RedisKeys<T> redisKey, String... keySuffix) {
        RedisCacheService redisCacheService = ContextUtils.getBean(RedisCacheService.class);
        redisCacheService.save(value, ttlInSec, redisKey, keySuffix);
    }

    public static <T> T load(RedisKeys<T> redisKey, String... keySuffix) {
        RedisCacheService redisCacheService = ContextUtils.getBean(RedisCacheService.class);
        return redisCacheService.load(redisKey, keySuffix);
    }

    public static <T> void remove(RedisKeys<T> redisKey, String... keySuffix) {
        RedisCacheService redisCacheService = ContextUtils.getBean(RedisCacheService.class);
        redisCacheService.remove(redisKey, keySuffix);
    }

}
