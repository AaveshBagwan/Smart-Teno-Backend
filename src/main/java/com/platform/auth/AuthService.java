package com.platform.auth;

import com.platform.repository.UsersRepository;
import com.platform.common.cache.LocalCacheService;
import com.platform.common.exception.BadRequestException;
import com.platform.common.model.CommonConstants;
import com.platform.common.model.StatusEnum;
import com.platform.common.model.UserSession;
import com.platform.common.utils.JwtUtils;
import com.platform.common.utils.SessionUtils;
import com.platform.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;

    private final LocalCacheService localCacheService;

    public AuthResModel login(AuthReqModel authReqModel) {
        User userFoundAtOurEnd = usersRepository.findByMobileNumberAndStatusAllIgnoreCase(authReqModel.getMobileNumber(), StatusEnum.A.name())
                .orElseThrow(() -> new BadRequestException("User not found"));

        Optional.ofNullable(userFoundAtOurEnd)
                .filter(user -> user.getPassword().equals(authReqModel.getPassword()))
                .orElseThrow(() -> new BadRequestException("Wrong password"));

        String acToken = JwtUtils.generateAccessToken(userFoundAtOurEnd);
        UserSession userSession = buildUserSession(userFoundAtOurEnd, acToken);
        SessionUtils.setUserSession(userSession);

        return buildAuthResModel(userFoundAtOurEnd, userSession);
    }

    public AuthResModel signup(AuthReqModel authReqModel) {
        usersRepository.findByMobileNumberAndStatusAllIgnoreCase(authReqModel.getMobileNumber(),
                StatusEnum.A.name()).ifPresent(user -> {
            throw new BadRequestException("user already exists");
        });

        User newUser = User.builder()
                .mobileNumber(authReqModel.getMobileNumber())
                .username(authReqModel.getUsername())
                .password(authReqModel.getPassword())
                .status(StatusEnum.A)
                .build();

        newUser = usersRepository.save(newUser);

        String acToken = JwtUtils.generateAccessToken(newUser);
        UserSession userSession = buildUserSession(newUser, acToken);
        SessionUtils.setUserSession(userSession);

        return buildAuthResModel(newUser, userSession);
    }

    public void logout() {
        SessionUtils.removeUserSession();
    }

    private static AuthResModel buildAuthResModel(User user, UserSession userSession) {
        return AuthResModel.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .username(user.getUsername())
                .accessToken(userSession.getAccessToken())
                .acTokenExpiry(userSession.getAcTokenExpiry())
                .acTokenType(CommonConstants.bearerTokenKey)
                .build();
    }

    private static UserSession buildUserSession(User user, String acToken) {
        Instant expiryAcToken = JwtUtils.extractExpiryFromJWT(acToken);
        return UserSession.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .username(user.getUsername())
                .accessToken(acToken)
                .acTokenExpiry(expiryAcToken.atZone(ZoneId.systemDefault()))
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.ofInstant(expiryAcToken, ZoneId.systemDefault()))
                .build();
    }

}
