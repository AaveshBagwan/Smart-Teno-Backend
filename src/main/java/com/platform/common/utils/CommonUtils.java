package com.platform.common.utils;

import com.platform.common.constants.RedisKeys;
import com.platform.common.cache.LocalCacheService;
import com.platform.common.model.UserSession;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class CommonUtils {

    public static UserSession getUserSession() {
        return Optional.ofNullable(RequestUtils.getAccessToken())
                .filter(StringUtils::hasText)
                .map((validToken) -> AppSpringContext.getBean(LocalCacheService.class).load(validToken))
                .map((smtSessionStr) -> JsonUtils.deserialize(smtSessionStr, UserSession.class))
                .orElse(null);
    }

    public static void updateUserSession(UserSession userSession) {
        if (userSession == null) return;
        Optional.ofNullable(RequestUtils.getAccessToken())
                .filter(StringUtils::hasText)
                .ifPresentOrElse(
                        (currentAccessToken) -> {
                            AppSpringContext.getBean(LocalCacheService.class)
                                    .save(RedisKeys.ACCESS_TOKEN.getKey(currentAccessToken), userSession);
                        },
                        () -> {
                            throw new IllegalArgumentException("Access token not found");
                        }
                );
    }

    public static void setUserSession(UserSession userSession) {
        if (userSession == null) {
            return;
        }
        if (!StringUtils.hasText(userSession.getAccessToken())) {
            throw new IllegalArgumentException("Access token must not be null or empty");
        }
        AppSpringContext.getBean(LocalCacheService.class)
                .save(RedisKeys.ACCESS_TOKEN.getKey(userSession.getAccessToken()), userSession);
    }

    public static String getProperty(String name) {
        Environment environment = AppSpringContext.getBean(Environment.class);
        return environment.getProperty(name);
    }

    public static <T> T getProperty(String name, Class<T> type) {
        Environment environment = AppSpringContext.getBean(Environment.class);
        return environment.getProperty(name,type);
    }

}
