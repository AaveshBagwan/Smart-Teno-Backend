package com.platform.common.utils;

import com.platform.common.model.CommonConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

@Slf4j
public class CommonUtils {

    public static void logExceptions(Exception e, HttpServletRequest request) {
        log.error(CommonConstants.EXCEPTION_AT_STR + "{}", request.getRequestURI());
        log.error(CommonConstants.EXCEPTION_MSG_STR + "{}", e.getMessage());
        log.error(CommonConstants.EXCEPTION_TRACE_STR + "{}", Arrays.toString(e.getStackTrace()));
        e.printStackTrace();
    }

    public static boolean isAuthExcludedUrl(String uri) {
        String[] excludedUrls = ContextUtils.getProperty("auth.excluded.urls", String[].class);
        AntPathMatcher matcher = new AntPathMatcher();
        for (String excludedUrlPatterns : excludedUrls) {
            if (matcher.match(excludedUrlPatterns, uri)) {
                return true;
            }
        }
        return false;
    }

}
