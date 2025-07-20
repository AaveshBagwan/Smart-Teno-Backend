package com.platform.common.exception;

import com.platform.common.utils.CommonUtils;
import com.platform.common.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CustomErrorHandler {

    public static void handleError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        CommonUtils.logExceptions(ex, request);
        if (ex instanceof BadRequestException) {
            ResponseUtils.addErrorResponse(response, "BAD_REQUEST", ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (ex instanceof IllegalArgumentException) {
            ResponseUtils.addErrorResponse(response, "BAD_REQUEST", ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (ex instanceof UnauthorizedException) {
            ResponseUtils.addErrorResponse(response, "UNAUTHORISED", ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } else if (ex instanceof ForbiddenException) {
            ResponseUtils.addErrorResponse(response, "FORBIDDEN", ex.getMessage(), HttpStatus.FORBIDDEN);
        } else if (ex instanceof MethodArgumentNotValidException) {
            List<String> errorMessage = ((MethodArgumentNotValidException) ex).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            ResponseUtils.addErrorResponse(response, "VALIDATION_ERROR", errorMessage, HttpStatus.BAD_REQUEST);
        } else {
            ResponseUtils.addErrorResponse(response, "INTERNAL_SERVER_ERROR", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

