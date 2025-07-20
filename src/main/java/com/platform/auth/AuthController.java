package com.platform.auth;

import com.platform.common.model.ResponseDTO;
import com.platform.common.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Validated({AuthReqModel.LoginValidationsGroup.class}) AuthReqModel authReqModel) {
        return ResponseUtils.sendResponse(
                authService.login(authReqModel),
                HttpStatus.OK
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody @Validated({AuthReqModel.SignupValidationsGroup.class}) AuthReqModel authReqModel) {
        return ResponseUtils.sendResponse(
                authService.signup(authReqModel),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout() {
        authService.logout();
        return ResponseUtils.sendResponse(
                "Logout successful",
                HttpStatus.OK
        );
    }

}
