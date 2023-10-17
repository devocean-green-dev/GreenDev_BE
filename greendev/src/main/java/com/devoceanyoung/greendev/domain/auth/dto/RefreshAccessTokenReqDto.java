package com.devoceanyoung.greendev.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshAccessTokenReqDto {

    @NotBlank(message = "RefreshToken은 필수로 입력되어야 합니다.")
    private String refreshToken;

    public RefreshAccessTokenReqDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
