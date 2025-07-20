package com.platform.common.filters;

import com.platform.common.exception.BadRequestException;
import com.platform.common.exception.CustomErrorHandler;
import com.platform.common.exception.UnauthorizedException;
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

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE+1)
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("ExceptionHandlerFilter started for request: {}", request.getRequestURI());

            // Proceed with the filter chain
            filterChain.doFilter(request, response);

            log.info("ExceptionHandlerFilter ended for request: {}", request.getRequestURI());
        } catch (Exception e) {
            CustomErrorHandler.handleError(request, response, e);
        }
    }

}
