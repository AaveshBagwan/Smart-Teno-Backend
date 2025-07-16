package com.platform.common.constants;

import com.platform.common.utils.CommonUtils;
import com.platform.common.utils.RequestUtils;
import jakarta.servlet.ServletRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Supplier;

public enum CookieKeys {
    ACCESS_TOKEN(() -> CommonUtils.getProperty("cookies.key-prefix.access-token")),
    REFRESH_TOKEN(() -> CommonUtils.getProperty("cookies.key-prefix.refresh-token")),
    USERNAME(() -> CommonUtils.getProperty("cookies.key-prefix.username"));

    private final Supplier<String> keyPrefix;
    private String keyPrefixCache = null;

    private String getKeyPrefix() {
        if(StringUtils.hasText(keyPrefixCache)) {
            return keyPrefixCache;
        }
        return keyPrefixCache = this.keyPrefix.get().toLowerCase();
    }

    private CookieKeys(Supplier<String> keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String getKey(String...keySuffixes) {
        StringBuilder key = new StringBuilder(getKeyPrefix());
        for (String keySuffix : keySuffixes){
            if (StringUtils.hasText(keySuffix)) {
                key.append("_").append(keySuffix);
            }
        }

        String serverName = Optional.ofNullable(RequestUtils.getCurrentRequest())
                .map(ServletRequest::getServerName)
                .filter(StringUtils::hasText)
                .orElse("");

        if(StringUtils.hasText(serverName)){
            key.append("_").append(serverName);
        }

        return key.toString();
    }
}
