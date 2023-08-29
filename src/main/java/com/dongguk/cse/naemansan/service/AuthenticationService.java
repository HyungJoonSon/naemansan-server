package com.dongguk.cse.naemansan.service;

import com.dongguk.cse.naemansan.common.ErrorCode;
import com.dongguk.cse.naemansan.common.RestApiException;
import com.dongguk.cse.naemansan.domain.*;
import com.dongguk.cse.naemansan.domain.type.EUserRole;
import com.dongguk.cse.naemansan.domain.type.ImageUseType;
import com.dongguk.cse.naemansan.domain.type.ELoginProvider;
import com.dongguk.cse.naemansan.dto.response.JwtResponseDto;
import com.dongguk.cse.naemansan.repository.ImageRepository;
import com.dongguk.cse.naemansan.repository.UserRepository;
import com.dongguk.cse.naemansan.security.jwt.JwtProvider;
import com.dongguk.cse.naemansan.security.jwt.JwtToken;
import com.dongguk.cse.naemansan.util.Oauth2Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final JwtProvider jwtProvider;
    private final Oauth2Util oauth2Util;

    public String getRedirectUrl(ELoginProvider ELoginProvider) {
        switch (ELoginProvider) {
            case KAKAO -> {
                return oauth2Util.getKakaoRedirectUrl();
            }
            case GOOGLE -> {
                return oauth2Util.getGoogleRedirectUrl();
            }
            case APPLE -> {
                return oauth2Util.getAppleRedirectUrl();
            }
        }
        return null;
    }

    public String getAccessToken(String authorizationCode, ELoginProvider ELoginProvider) {
        String accessToken = null;
        switch (ELoginProvider) {
            case KAKAO -> {
                accessToken = oauth2Util.getKakaoAccessToken(authorizationCode);
            }
            case GOOGLE -> {
                accessToken = oauth2Util.getGoogleAccessToken(authorizationCode);
            }
            case APPLE -> {
                accessToken = authorizationCode;
            }
        }
        return accessToken;
    }

    public JwtResponseDto login(String accessToken, ELoginProvider provider) {
        // Load User Data in Oauth Server
        String socialId = null;
        switch (provider) {
            case KAKAO -> {
                socialId = oauth2Util.getKakaoUserInformation(accessToken);
            }
            case GOOGLE -> {
                socialId = oauth2Util.getGoogleUserInformation(accessToken);
            }
            case APPLE -> {
                socialId = oauth2Util.getAppleUserInformation(accessToken);
            }
        }

        // User Data 존재 여부 확인
        if (socialId == null) { throw new RestApiException(ErrorCode.NOT_FOUND_USER); }

        // 랜덤 닉네임 생성
        Random random = new Random();

        // User 탐색
        final String serialId = socialId;
        User user = userRepository.findBySerialIdAndProvider(socialId, provider)
                .orElseGet(() -> {
                    String userName = provider.toString() + "-";
                    for (int i = 0; i < 3; i++) {
                        userName += String.format("%04d", random.nextInt(1000));
                    }

                    User loginUser = userRepository.save(User.builder()
                            .serialId(serialId)
                            .nickname(userName)
                            .provider(provider)
                            .role(EUserRole.USER)
                            .build());

                    imageRepository.save(Image.builder()
                            .useObject(loginUser)
                            .imageUseType(ImageUseType.USER)
                            .originName("default_image.png")
                            .uuidName("0_default_image.png")
                            .type("image/png").build());

                    return loginUser;
                });

        // JwtToken 생성, 기존 Refresh Token 탐색
        JwtToken jwtToken = jwtProvider.createTotalToken(user.getId(), user.getRole());

        user.updateRefreshToken(jwtToken.getRefresh_token());

        // Jwt 반환
        return JwtResponseDto.builder()
                .jwt(jwtToken)
                .build();
    }

    public Boolean logout(Long userId) {
        User user =  userRepository.findByIdAndIsLoginAndRefreshTokenIsNotNull(userId, true).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER));
        user.logoutUser();
        return Boolean.TRUE;
    }

    public Map<String, String> getAccessTokenByRefreshToken(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", jwtProvider.validRefreshToken(request));
        return map;
    }
}
