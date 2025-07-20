package com.platform.user;

import com.platform.common.exception.BadRequestException;
import com.platform.common.model.ResponseDTO;
import com.platform.common.model.UserSession;
import com.platform.common.utils.CommonUtils;
import com.platform.common.utils.ResponseUtils;
import com.platform.common.utils.SessionUtils;
import com.platform.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody @Validated(User.UpdateUserGroup.class) User user) {
        Long userId = Optional.ofNullable(SessionUtils.getUserSession())
                .map(UserSession::getUserId)
                .orElseThrow(() -> new BadRequestException("userId not found"));

        user.setUserId(userId);

        return ResponseUtils.sendResponse(
                userService.updateUser(user),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getUser() {
        Long userId = Optional.ofNullable(SessionUtils.getUserSession())
                .map(UserSession::getUserId)
                .orElseThrow(() -> new BadRequestException("userId not found"));

        return ResponseUtils.sendResponse(
                userService.getUser(userId),
                HttpStatus.OK
        );
    }

}
