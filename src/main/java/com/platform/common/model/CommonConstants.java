package com.platform.common.model;

public interface CommonConstants {

    String[] excludedUrls = {
            "/smt/public/**",
            "/error/**",
            "/callback/**",
            "/favicon.ico",
            "/smt/test/**",
            "/smt/auth/test/**",
            "/smt/actuator/**",
            "/smt/swagger-ui/**",
            "/v3/api-docs/**",
            "/smt/auth/login",
            "/smt/auth/signup",
    };

    String bearerTokenKey = "Bearer";

    String SUCCESS = "SUCCESS";
    String FAILURE = "FAILURE";

    String EXCEPTION_AT_STR = "EXCEPTION_OCCURRED_AT : ";
    String EXCEPTION_MSG_STR = "EXCEPTION_MESSAGE : ";
    String EXCEPTION_TRACE_STR = "EXCEPTION_STACK_TRACE : ";

    String LANDING_TIME = "landingTime";
}
