package com.devoceanyoung.greendev.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResDto {
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenRemaininTime;

    @Builder
    public TokenResDto(String accessToken, String refreshToken, Long refreshTokenRemaininTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenRemaininTime = refreshTokenRemaininTime;
    }
}
