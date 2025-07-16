package com.platform.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseMessage {
    private ZonedDateTime landingTime;
    private ZonedDateTime responseTime;
    private String status;
    private HttpStatus httpStatus;
    private String endpoint;
}
