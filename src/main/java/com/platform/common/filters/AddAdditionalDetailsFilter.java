package com.platform.common.filters;

import com.platform.common.model.CommonConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.ZonedDateTime;

@Component
@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE + 2)
public class AddAdditionalDetailsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("AddAdditionalDetailsFilter started for request: {}", request.getRequestURI());

        /* Add landing time */
        request.setAttribute(CommonConstants.LANDING_TIME, ZonedDateTime.now());

        filterChain.doFilter(request, response);

        log.info("AddAdditionalDetailsFilter ended for request: {}", request.getRequestURI());
    }

}
