package com.platform.auth;

import com.platform.common.annotations.ValidMobileNumber;
import com.platform.common.annotations.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthReqModel {

    @NotBlank(message = "username is mandatory", groups = {SignupValidationsGroup.class})
    private String username;

    @ValidMobileNumber(groups = {SignupValidationsGroup.class, LoginValidationsGroup.class})
    @NotBlank(message = "mobile number is mandatory", groups = {SignupValidationsGroup.class, LoginValidationsGroup.class})
    private String mobileNumber;

    @ValidPassword(groups = {SignupValidationsGroup.class, LoginValidationsGroup.class})
    @NotBlank(message = "password is mandatory", groups = {SignupValidationsGroup.class, LoginValidationsGroup.class})
    private String password;

    public interface SignupValidationsGroup{}
    public interface LoginValidationsGroup{}

}
