package com.platform.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResModel {

    private Long userId;

    private String username;

    private String email;

    private String mobileNumber;

    private String accessToken;

    private ZonedDateTime acTokenExpiry;

    private String acTokenType;

}
