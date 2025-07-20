package com.platform.common.exception;

import com.platform.common.model.ResponseDTO;
import com.platform.common.utils.CommonUtils;
import com.platform.common.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDTO> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        CommonUtils.logExceptions(ex, request);
        return ResponseUtils.sendErrorResponse("BAD_REQUEST", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        CommonUtils.logExceptions(ex, request);
        return ResponseUtils.sendErrorResponse("BAD_REQUEST", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEnumValueFromJsonException.class)
    public ResponseEntity<ResponseDTO> handleInvalidEnumValueException(InvalidEnumValueFromJsonException ex, HttpServletRequest request) {
        CommonUtils.logExceptions(ex, request);
        return ResponseUtils.sendErrorResponse("INVALID_FIELD_VALUE", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEnumValueFromDbException.class)
    public ResponseEntity<ResponseDTO> handleInvalidEnumValueFromDbException(InvalidEnumValueFromDbException ex, HttpServletRequest request) {
        CommonUtils.logExceptions(ex, request);
        return ResponseUtils.sendErrorResponse("INVALID_FIELD_VALUE", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ResponseDTO> handleNotFoundExceptions(Exception ex, HttpServletRequest request) {
        String errorMessage = (ex instanceof NoHandlerFoundException)
                ? "The requested URL was not found: " + ((NoHandlerFoundException) ex).getRequestURL()
                : ex.getMessage();
        CommonUtils.logExceptions(ex, request);
        return ResponseUtils.sendErrorResponse("NOT_FOUND", errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        CommonUtils.logExceptions(ex, request);
        return ResponseUtils.sendErrorResponse("VALIDATION_ERROR", errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDTO> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        CommonUtils.logExceptions(ex, request);
        return ResponseUtils.sendErrorResponse("UNAUTHORISED", ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> handleInvalidJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        CommonUtils.logExceptions(ex, request);
        return ResponseUtils.sendErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleException(Exception ex, HttpServletRequest request) {
        CommonUtils.logExceptions(ex, request);
        return ResponseUtils.sendErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
