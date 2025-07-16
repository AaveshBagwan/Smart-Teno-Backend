package com.platform.user;

import com.platform.common.model.ResponseDTO;
import com.platform.common.utils.ResponseUtils;
import com.platform.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody @Validated(User.UpdateUserGroup.class) User user) {
        return ResponseUtils.sendResponse(
                userService.updateUser(user),
                HttpStatus.OK
        );
    }

}
