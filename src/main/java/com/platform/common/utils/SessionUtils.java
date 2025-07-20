package com.platform.common.utils;

import com.platform.common.exception.BadRequestException;
import com.platform.common.model.redis.RedisKeys;
import com.platform.common.model.UserSession;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class SessionUtils {

    public static UserSession getUserSession() {
        return RedisUtils.load(RedisKeys.ACCESS_TOKEN, RequestUtils.getAccessToken());
    }

    public static void removeUserSession() {
        RedisUtils.remove(RedisKeys.ACCESS_TOKEN, RequestUtils.getAccessToken());
    }

    public static void updateUserSession(UserSession userSession) {
        if (userSession == null) return;

        String acToken = Optional.ofNullable(RequestUtils.getAccessToken())
                .filter(StringUtils::hasText)
                .orElseThrow(() -> new BadRequestException("Access token not found"));

        RedisUtils.save(userSession, RedisKeys.ACCESS_TOKEN, acToken);
    }

    public static void setUserSession(UserSession userSession) {
        if (userSession == null) {
            return;
        }
        if (!StringUtils.hasText(userSession.getAccessToken())) {
            throw new IllegalArgumentException("Access token must not be null or empty");
        }
        RedisUtils.save(userSession, RedisKeys.ACCESS_TOKEN, userSession.getAccessToken());
    }

}
