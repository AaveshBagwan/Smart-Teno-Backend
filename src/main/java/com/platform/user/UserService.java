package com.platform.user;

import com.platform.Repository.UsersRepository;
import com.platform.auth.AuthResModel;
import com.platform.common.exception.BadRequestException;
import com.platform.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public AuthResModel updateUser(User user) {
        User foundUser = usersRepository.findById(user.getUserId())
                .orElseThrow(() -> new BadRequestException("User not found with id " + user.getUserId()));

        if (StringUtils.hasText(user.getUsername())) {
            foundUser.setUsername(user.getUsername());
        }
        if (StringUtils.hasText(user.getEmail())) {
            foundUser.setEmail(user.getEmail());
        }
        if (StringUtils.hasText(user.getAddress())) {
            foundUser.setEmail(user.getAddress());
        }
        if (StringUtils.hasText(user.getProfileImageUrl())) {
            foundUser.setEmail(user.getProfileImageUrl());
        }
        if (StringUtils.hasText(user.getPassword())) {
            if (!foundUser.getPassword().equals(user.getPassword())) {
                throw new BadRequestException("Existing password entered is invalid");
            }
            foundUser.setPassword(user.getPassword());
        }
        return AuthResModel.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImageUrl(user.getProfileImageUrl())
                .mobileNumber(user.getMobileNumber())
                .build();
    }
}
