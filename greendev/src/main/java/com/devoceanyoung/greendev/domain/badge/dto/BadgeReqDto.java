package com.devoceanyoung.greendev.domain.badge.dto;

import com.devoceanyoung.greendev.domain.badge.domain.Badge;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BadgeReqDto {
	private String badgeName;
	private String badgeImageUrl;
	private Integer level;

	@Builder
	public BadgeReqDto(String badgeName, String badgeImageUrl, Integer level) {
		this.badgeName = badgeName;
		this.badgeImageUrl = badgeImageUrl;
		this.level = level;
	}

	public Badge toEntity(){
		return Badge.builder()
			.badgeImageUrl(badgeImageUrl)
			.badgeName(badgeName)
			.level(level)
			.build();
	}
}
