package com.platform.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {
    private Long userId;
    private String username;
    private String email;
    private String mobileNumber;
    private String profileImageUrl;
    private String accessToken;
    private ZonedDateTime acTokenExpiry;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
