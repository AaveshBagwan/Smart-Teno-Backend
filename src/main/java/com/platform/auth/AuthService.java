package com.platform.auth;

import com.platform.Repository.UsersRepository;
import com.platform.common.constants.CommonConstants;
import com.platform.common.constants.RedisKeys;
import com.platform.common.constants.StatusEnum;
import com.platform.common.exception.BadRequestException;
import com.platform.common.cache.LocalCacheService;
import com.platform.common.model.UserSession;
import com.platform.common.utils.CommonUtils;
import com.platform.common.utils.JwtUtils;
import com.platform.common.utils.RequestUtils;
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
        CommonUtils.setUserSession(userSession);

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
        CommonUtils.setUserSession(userSession);

        return buildAuthResModel(newUser, userSession);
    }

    public void logout() {
        Optional.ofNullable(RequestUtils.getCurrentRequest())
                .map(request -> request.getAttribute(RedisKeys.ACCESS_TOKEN.getKey()))
                .ifPresent(token -> localCacheService.remove(RedisKeys.ACCESS_TOKEN.getKey((String) token)));
    }

    private static AuthResModel buildAuthResModel(User user, UserSession userSession) {
        return AuthResModel.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .username(user.getUsername())
                .profileImageUrl(null)
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
