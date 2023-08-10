package com.devoceanyoung.greendev.domain.badge.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "badge_id", updatable = false)
	private Long badgeId;

	private String badgeImageUrl;
	private String badgeName;

	private Integer level;

	@Builder
	public Badge(String badgeImageUrl, Integer level, String badgeName) {
		this.badgeImageUrl = badgeImageUrl;
		this.level = level;
		this.badgeName = badgeName;
	}

}

