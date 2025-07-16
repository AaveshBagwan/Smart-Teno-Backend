package com.platform.common.filters;

import com.platform.common.constants.CommonConstants;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Component
@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class AddRequestInfoFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("AddAdditionalDetailsFilter_started");

            /* Add landing time */
            request.setAttribute(CommonConstants.LANDING_TIME, ZonedDateTime.now());

            filterChain.doFilter(request, response);
            log.info("AddAdditionalDetailsFilter_ended");
        } catch (UnauthorizedException e) {
            log.error("AddAdditionalDetailsFilter_UnauthorizedException : {}", e.getMessage());
            CustomErrorHandler.handleError(request, response, e);
        } catch (BadRequestException e) {
            log.error("AddAdditionalDetailsFilter_BadRequestException : {}", e.getMessage());
            CustomErrorHandler.handleError(request, response, e);
        } catch (Exception e) {
            log.error("AddAdditionalDetailsFilter_Exception : {}", e.getMessage());
            CustomErrorHandler.handleError(request, response, e);
        }
    }

}
