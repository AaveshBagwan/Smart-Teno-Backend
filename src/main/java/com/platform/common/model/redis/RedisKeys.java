package com.platform.common.model.redis;

import com.platform.common.model.UserSession;

public class  RedisKeys<T> {

    public static final RedisKeys<UserSession> ACCESS_TOKEN = new RedisKeys<>("redis.key-prefix.access-token", UserSession.class);
    public static final RedisKeys<UserSession> REFRESH_TOKEN = new RedisKeys<>("redis.key-prefix.refresh-token", UserSession.class);

    private final KeyInfo<T> keyInfo;

    private RedisKeys(String keyPrefix, Class<T> valueType) {
        this.keyInfo = RedisKeyInfo.of(keyPrefix, valueType);
    }

    public KeyInfo<T> info() {
        return this.keyInfo;
    }

}
