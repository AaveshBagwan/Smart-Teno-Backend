package com.platform.common.utils;

import com.platform.common.constants.CommonConstants;
import com.platform.common.model.ErrorData;
import com.platform.common.model.ResponseDTO;
import com.platform.common.model.ResponseMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class ResponseUtils {

    private ResponseUtils() {
        // Private constructor to prevent instantiation
    }

    public static ResponseEntity<ResponseDTO> sendResponse(Object object, HttpStatus status) {
        ResponseMessage responseMessage = ResponseMessage.builder()
                .landingTime(RequestUtils.getLandingTime())
                .responseTime(ZonedDateTime.now())
                .httpStatus(status)
                .status(CommonConstants.SUCCESS)
                .endpoint(RequestUtils.getCurrentRequest().getRequestURI())
                .build();

        ResponseDTO response = ResponseDTO.builder()
                .responseData(object)
                .responseMessage(responseMessage)
                .build();

        return new ResponseEntity<ResponseDTO>(response, status);
    }

    public static ResponseEntity<ResponseDTO> sendResponse(HttpHeaders headers, Object object, HttpStatus status) {
        ResponseMessage responseMessage = ResponseMessage.builder()
                .landingTime(RequestUtils.getLandingTime())
                .responseTime(ZonedDateTime.now())
                .httpStatus(status)
                .status(CommonConstants.SUCCESS)
                .endpoint(RequestUtils.getCurrentRequest().getRequestURI())
                .build();

        ResponseDTO response = ResponseDTO.builder()
                .responseMessage(responseMessage)
                .responseData(object)
                .build();

        return new ResponseEntity<ResponseDTO>(response, headers, status);
    }

    public static ResponseEntity<ResponseDTO> sendErrorResponse(String errorCode, Object errorMessage, HttpStatus status) {

        ResponseMessage responseMessage = ResponseMessage.builder()
                .landingTime(RequestUtils.getLandingTime())
                .responseTime(ZonedDateTime.now())
                .httpStatus(status)
                .status(CommonConstants.FAILURE)
                .endpoint(RequestUtils.getCurrentRequest().getRequestURI())
                .build();

        ErrorData errorData = ErrorData.builder()
                .errorMessage(errorMessage)
                .errorCode(errorCode)
                .build();

        ResponseDTO errorResponse = ResponseDTO.builder()
                .responseMessage(responseMessage)
                .errorData(errorData)
                .build();

        return new ResponseEntity<ResponseDTO>(errorResponse, status);
    }

    @SneakyThrows
    public static void addErrorResponse(HttpServletResponse response, String errorCode, String errorMessage, HttpStatus status)  {
        ResponseDTO responseData = ResponseUtils.sendErrorResponse(errorCode, errorMessage, status).getBody();
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getOutputStream().write(JsonUtils.serialize(responseData).getBytes());
    }

}
