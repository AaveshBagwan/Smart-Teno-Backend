package com.platform.common.filters;

import com.platform.common.exception.UnauthorizedException;
import com.platform.common.utils.CommonUtils;
import com.platform.common.utils.JwtUtils;
import com.platform.common.utils.RequestUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class AuthorisationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("AuthorisationFilter started for request: {}", request.getRequestURI());

        if (CommonUtils.isAuthExcludedUrl(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = RequestUtils.getAccessToken();
        if (!StringUtils.hasText(accessToken)) {
            throw new UnauthorizedException("Token not found!");
        }

        /*
         *  This method will validate if access token is valid or not,
         *  if not valid it will throw Exception
         */
        JwtUtils.validateAccessToken(accessToken);

        filterChain.doFilter(request, response);
        log.info("AuthorisationFilter ended for request: {}", request.getRequestURI());
    }

}
