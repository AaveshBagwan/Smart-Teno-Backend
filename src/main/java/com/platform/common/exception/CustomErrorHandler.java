package com.platform.common.exception;

import com.platform.common.constants.CommonConstants;
import com.platform.common.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class CustomErrorHandler {

    private void logExceptions(Exception e, HttpServletRequest request) {
        log.error(CommonConstants.EXCEPTION_AT_STR + "{}", request.getRequestURI());
        log.error(CommonConstants.EXCEPTION_MSG_STR + "{}", e.getMessage());
        log.error(CommonConstants.EXCEPTION_TRACE_STR + "{}", Arrays.toString(e.getStackTrace()));
        e.printStackTrace();
    }

    public static void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        if (e instanceof BadRequestException) {
            ResponseUtils.addErrorResponse(response, "BAD_REQUEST", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        } else if (e instanceof UnauthorizedException) {
            ResponseUtils.addErrorResponse(response, "UNAUTHORISED", e.getMessage(),
                    HttpStatus.UNAUTHORIZED);
        } else if (e instanceof ForbiddenException) {
            ResponseUtils.addErrorResponse(response, "FORBIDDEN", e.getMessage(),
                    HttpStatus.FORBIDDEN);
        } else {
            ResponseUtils.addErrorResponse(response, "INTERNAL_SERVER_ERROR", e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

