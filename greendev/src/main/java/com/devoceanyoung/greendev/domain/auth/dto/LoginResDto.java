package com.devoceanyoung.greendev.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResDto {
	private String nickname;
	private String accessToken;

	@Builder
	public LoginResDto(String nickname, String accessToken) {
		this.nickname = nickname;
		this.accessToken = accessToken;
	}
}
