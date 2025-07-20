package com.platform.common.utils;

import com.platform.common.model.CommonConstants;
import com.platform.common.model.CookieKeys;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class RequestUtils {

    private RequestUtils() {
        // Private constructor to prevent instantiation
    }

    public static @Nullable HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static String getAccessToken() {
        HttpServletRequest request = getCurrentRequest();

        String tokenFromAuthHeader = Optional.ofNullable(request)
                .filter(val -> StringUtils.hasText(val.getHeader(HttpHeaders.AUTHORIZATION)))
                .map(val -> val.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(val -> val.startsWith(CommonConstants.bearerTokenKey))
                .map(val -> val.substring(CommonConstants.bearerTokenKey.length() + 1))
                .map(String::trim)
                .orElse("");

        if (StringUtils.hasText(tokenFromAuthHeader)) {
            return tokenFromAuthHeader;
        }

        String tokenFromCookies = Optional.ofNullable(request)
                .map(HttpServletRequest::getCookies)
                .flatMap(val ->
                        Arrays.stream(val)
                                .filter(cookie -> cookie.getName().equalsIgnoreCase(CookieKeys.ACCESS_TOKEN.getKey(request.getServerName())))
                                .findFirst()
                )
                .map(Cookie::getValue)
                .map(String::trim)
                .orElse("");

        if (StringUtils.hasText(tokenFromCookies)) {
            return tokenFromCookies;
        }

        return null;
    }

    public static ZonedDateTime getLandingTime() {
        return Optional.ofNullable(getCurrentRequest())
                .map(request -> request.getAttribute(CommonConstants.LANDING_TIME))
                .filter(val -> val instanceof ZonedDateTime)
                .map(val -> (ZonedDateTime) val)
                .orElse(null);
    }

}
