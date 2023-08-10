package com.devoceanyoung.greendev.domain.badge.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileUpdateReqDto {
	List<SingleBadge> badges;
	@Getter
	@AllArgsConstructor
	public static class SingleBadge{
		Long badgeInstanceId;
		Integer position;
	}
}
