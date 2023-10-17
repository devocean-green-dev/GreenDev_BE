package com.devoceanyoung.greendev.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenResDto {
    private String refreshToken;
    private Long refreshTokenRemaininTime;

    public RefreshTokenResDto(String refreshToken, Long refreshTokenRemaininTime) {
        this.refreshToken = refreshToken;
        this.refreshTokenRemaininTime = refreshTokenRemaininTime;
    }
}
