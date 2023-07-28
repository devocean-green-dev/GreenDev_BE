package com.devoceanyoung.greendev.domain.member.dto;

import com.devoceanyoung.greendev.domain.member.domain.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResDto {
	private String email;
	private String nickname;
	private String profileImageUrl;

	public static MemberResDto of(Member member){
		return MemberResDto.builder()
			.email(member.getEmail())
			.nickname(member.getNickname())
			.profileImageUrl(member.getProfileImageUrl())
			.build();
	}


}
