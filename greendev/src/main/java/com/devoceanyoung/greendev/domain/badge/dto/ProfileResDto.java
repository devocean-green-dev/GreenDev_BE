package com.devoceanyoung.greendev.domain.badge.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.devoceanyoung.greendev.domain.badge.domain.BadgeInstance;
import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.campaign.dto.CampaignResDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileResDto {
	private List<ProfileResDto.SingleBadge> badges;
	private Integer count;
	@Getter
	public static class SingleBadge {// 단일 캠페인
		private Long badgeInstanceId;
		private Integer position;
		private String badgeImageUrl;
		private String badgeName;

		public SingleBadge(BadgeInstance badgeInstance) {
			this.badgeInstanceId = badgeInstance.getBadgeInstanceId();
			this.position = badgeInstance.getPosition();
			this.badgeImageUrl = badgeInstance.getBadge().getBadgeImageUrl();
			this.badgeName = badgeInstance.getBadge().getBadgeName();
		}
		public static ProfileResDto.SingleBadge of(BadgeInstance badgeInstance) {
			return new ProfileResDto.SingleBadge(badgeInstance);
		}
	}


	public static ProfileResDto of(List<BadgeInstance> badgeInstances) {
		return ProfileResDto.builder()
			.badges(badgeInstances.stream()
				.map(ProfileResDto.SingleBadge::of)
				.collect(Collectors.toList()))
			.count(badgeInstances.size())
			.build();
	}
}
