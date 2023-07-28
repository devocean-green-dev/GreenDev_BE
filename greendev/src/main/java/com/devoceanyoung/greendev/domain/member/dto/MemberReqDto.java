package com.devoceanyoung.greendev.domain.member.dto;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberReqDto {

	@NotBlank(message = "닉네임은 필수입니다. ")
	private String nickname;

	public MemberReqDto(String nickname) {
		this.nickname = nickname;
	}
}
