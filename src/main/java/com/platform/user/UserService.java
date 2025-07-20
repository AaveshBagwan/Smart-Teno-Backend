package com.platform.user;

import com.platform.repository.UsersRepository;
import com.platform.common.exception.BadRequestException;
import com.platform.common.model.StatusEnum;
import com.platform.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    @Transactional
    public User updateUser(User user) {
        User foundUser = usersRepository.findById(user.getUserId())
                .orElseThrow(() -> new BadRequestException("User not found with id " + user.getUserId()));

        if (StringUtils.hasText(user.getUsername())) {
            foundUser.setUsername(user.getUsername());
        }
        if (StringUtils.hasText(user.getEmail())) {
            foundUser.setEmail(user.getEmail());
        }
        if (StringUtils.hasText(user.getAddress())) {
            foundUser.setAddress(user.getAddress());
        }
        if (StringUtils.hasText(user.getProfileImageUrl())) {
            foundUser.setProfileImageUrl(user.getProfileImageUrl());
        }
        if (StringUtils.hasText(user.getPassword()) && StringUtils.hasText(user.getNewPassword())) {
            if (!foundUser.getPassword().equals(user.getPassword())) {
                throw new BadRequestException("Existing password entered is invalid");
            }
            if(foundUser.getPassword().equals(user.getNewPassword())) {
                throw new BadRequestException("New password cannot be same as existing password");
            }
            foundUser.setPassword(user.getNewPassword());
        }

        return usersRepository.save(foundUser);
    }

    public User getUser(Long userId){
        return usersRepository.findByUserIdAndStatus(userId, StatusEnum.A)
                .orElseThrow(() -> new BadRequestException("User not found with id " + userId));
    }
}
