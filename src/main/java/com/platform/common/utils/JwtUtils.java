package com.platform.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.platform.common.model.jwt.JwtCredentials;
import com.platform.common.model.jwt.JwtSubKeys;
import com.platform.common.exception.UnauthorizedException;
import com.platform.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
public class JwtUtils {

    private static final Supplier<JwtCredentials> jwtCredentials = () -> AppSpringContext.getBean(JwtCredentials.class);
    private static JwtCredentials jwtCredentialsCache = null;

    private JwtUtils() {
        // Private constructor to prevent instantiation
    }

    public static JwtCredentials getJwtCredentials() {
        if (jwtCredentialsCache != null) {
            return jwtCredentialsCache;
        }
        return jwtCredentialsCache = jwtCredentials.get();
    }

    public static String generateAccessToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", user.getEmail());
        payload.put("userId", user.getUserId());
        payload.put("username", user.getUsername());
        payload.put("email", user.getEmail());

        String secretKey = getJwtCredentials().getAccessToken().get(JwtSubKeys.SECRET_KEY);
        long expiryInSec = Long.parseLong(getJwtCredentials().getAccessToken().get(JwtSubKeys.EXPIRY_IN_SECONDS));

        return generateJwtToken(secretKey, payload, expiryInSec);
    }

    public static DecodedJWT validateAccessToken(String accessToken) {
        String secretKey = getJwtCredentials().getAccessToken().get(JwtSubKeys.SECRET_KEY);
        return JwtUtils.validateToken(accessToken, secretKey);
    }

    public static String generateJwtToken(String secretKey, Map<String, Object> payload, long expiryInSec) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(expiryInSec);
        return JWT.create()
                .withPayload(payload)
                .withIssuer(getJwtCredentials().getIssuer())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public static DecodedJWT validateToken(String jwtToken, String secretKey) {
        try {
            Optional.ofNullable(jwtToken)
                    .filter(StringUtils::hasText)
                    .orElseThrow(() -> new IllegalArgumentException("JWT token cannot be null or empty"));

            return JWT.require(Algorithm.HMAC256(secretKey))
                    .withIssuer(getJwtCredentials().getIssuer())
                    .build()
                    .verify(jwtToken);

        } catch (SignatureVerificationException e) {
            throw new UnauthorizedException("Invalid JWT token signature");
        } catch (TokenExpiredException e) {
            throw new UnauthorizedException("JWT token has expired");
        } catch (InvalidClaimException e) {
            log.error("Error InvalidClaimException : {}", e.getMessage());
            log.error("Error InvalidClaimException cause : {}", e.getCause().getMessage());
            throw new UnauthorizedException(e.getCause().getMessage());
        } catch (Exception e) {
            throw new UnauthorizedException("Unable to validate JWT token");
        }
    }

    public static Instant extractExpiryFromJWT(String jwtToken) {
        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        return decodedJWT.getExpiresAtAsInstant();
    }

    public static String extractSubFromJWT(String jwtToken) {
        return extractClaimFromJWT(jwtToken, "sub");
    }

    public static String extractClaimFromJWT(String jwtToken, String claim) {
        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        return decodedJWT.getClaim("sub").asString();
    }

}
