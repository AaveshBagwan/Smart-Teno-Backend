package com.platform.common.model;

import com.platform.common.utils.ContextUtils;
import com.platform.common.utils.RequestUtils;
import jakarta.servlet.ServletRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Supplier;

public enum CookieKeys {
    ACCESS_TOKEN("cookies.key-prefix.access-token"),
    REFRESH_TOKEN("cookies.key-prefix.refresh-token"),
    USERNAME("cookies.key-prefix.username");

    private final Supplier<String> keyPrefix;
    private String keyPrefixCache = null;

    private String getKeyPrefix() {
        if (StringUtils.hasText(keyPrefixCache)) {
            return keyPrefixCache;
        }
        return keyPrefixCache = this.keyPrefix.get().toLowerCase();
    }

    private CookieKeys(String keyPrefix) {
        this.keyPrefix = () -> ContextUtils.getProperty(keyPrefix);
    }

    public String getKey(String... keySuffixes) {
        StringBuilder key = new StringBuilder(getKeyPrefix());
        for (String keySuffix : keySuffixes) {
            if (StringUtils.hasText(keySuffix)) {
                key.append("_").append(keySuffix);
            }
        }

        String serverName = Optional.ofNullable(RequestUtils.getCurrentRequest())
                .map(ServletRequest::getServerName)
                .filter(StringUtils::hasText)
                .orElse("");

        if (StringUtils.hasText(serverName)) {
            key.append("_").append(serverName);
        }

        return key.toString();
    }
}
