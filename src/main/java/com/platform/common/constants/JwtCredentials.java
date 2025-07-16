package com.platform.common.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtCredentials {
    private String issuer;
    private Map<JwtSubKeys, String> accessToken;
    private Map<JwtSubKeys, String> refreshToken;
}
