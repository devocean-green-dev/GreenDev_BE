package com.devoceanyoung.greendev.domain.badge.dto;

import com.devoceanyoung.greendev.domain.badge.domain.Badge;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BadgeResDto {
	private Long badgeId;
	private String badgeName;
	private String badgeImageUrl;
	private Integer level;

	public BadgeResDto(Badge badge) {
		this.badgeId = badge.getBadgeId();
		this.badgeName = badge.getBadgeName();
		this.badgeImageUrl = badge.getBadgeImageUrl();
		this.level = badge.getLevel();
	}
}
