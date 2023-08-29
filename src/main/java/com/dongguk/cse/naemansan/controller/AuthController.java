package com.dongguk.cse.naemansan.controller;

import com.dongguk.cse.naemansan.annotation.UserId;
import com.dongguk.cse.naemansan.exception.ResponseDto;
import com.dongguk.cse.naemansan.dto.response.JwtResponseDto;
import com.dongguk.cse.naemansan.domain.type.ELoginProvider;
import com.dongguk.cse.naemansan.dto.response.TokenDto;
import com.dongguk.cse.naemansan.security.jwt.JwtProvider;
import com.dongguk.cse.naemansan.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * Redirect URL을 반환한다.
     * @return Redirect URL
     */
    @GetMapping("/kakao")
    public ResponseDto<Map<String, String>> getKakaoRedirectUrl() {
        Map<String, String> map = new HashMap<>();
        map.put("url", authService.getRedirectUrl(ELoginProvider.KAKAO));
        return new ResponseDto<>(map);
    }

    @GetMapping("/google")
    public ResponseDto<Map<String, String>> getGoogleRedirectUrl() {
        Map<String, String> map = new HashMap<>();
        map.put("url", authService.getRedirectUrl(ELoginProvider.GOOGLE));
        return new ResponseDto<>(map);
    }

    @GetMapping("/apple")
    public ResponseDto<Map<String, String>> getAppleRedirectUrl() {
        Map<String, String> map = new HashMap<>();
        map.put("url", authService.getRedirectUrl(ELoginProvider.APPLE));
        return new ResponseDto<>(map);
    }

    /**
     * 로그인 요청을 처리한다.
     * @param request
     * @return JwtResponseDto
     */
    @PostMapping("/kakao")
    public ResponseDto<JwtResponseDto> loginKakao(HttpServletRequest request) {
        return new ResponseDto<>(authService.login(JwtProvider.refineToken(request), ELoginProvider.KAKAO));
    }

    @PostMapping("/google")
    public ResponseDto<JwtResponseDto> loginGoogle(HttpServletRequest request) {
        return new ResponseDto<>(authService.login(JwtProvider.refineToken(request), ELoginProvider.GOOGLE));
    }

    @PostMapping("/apple")
    public ResponseDto<JwtResponseDto> loginApple(HttpServletRequest request) {
        return new ResponseDto<>(authService.login(JwtProvider.refineToken(request), ELoginProvider.APPLE));
    }

    /**
     * 로그인 콜백을 처리해서 AccessToken을 반환한다.
     * @param code
     * @return AccessToken
     */
    @GetMapping("/kakao/callback")
    public ResponseDto<String> getKakaoAccessToken(@RequestParam("code") String code) {
        return new ResponseDto<>(authService.getAccessToken(code, ELoginProvider.KAKAO));
    }

    @GetMapping("/google/callback")
    public ResponseDto<String> loginGoogle(@RequestParam("code") String code) {
        return new ResponseDto<>(authService.getAccessToken(code, ELoginProvider.GOOGLE));
    }

    @PostMapping(value = "/apple/callback")
    public ResponseDto<?> loginApple(@RequestParam("code") String code) {
        return new ResponseDto<>(authService.getAccessToken(code, ELoginProvider.APPLE));
    }

    /**
     * 로그아웃 요청을 처리한다.
     * @param userId
     * @return Boolean
     */
    @GetMapping("/logout")
    public ResponseDto<Boolean> logout(@UserId Long userId) {
        return new ResponseDto<>(authService.logout(userId));
    }

    /**
     * AccessToken을 Refresh한다.
     * @param request
     * @return TokenDto
     */
    @PostMapping("/reissue")
    public ResponseDto<?> UpdateAccessToken(HttpServletRequest request) {
        return new ResponseDto<>(authService.getAccessTokenByRefreshToken(request));
    }
}