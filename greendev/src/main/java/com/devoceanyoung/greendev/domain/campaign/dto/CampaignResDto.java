package com.devoceanyoung.greendev.domain.campaign.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CampaignResDto {
	private List<CampaignResDto.SingleCampaign> campaigns;
	private Integer count;
	private Integer totalPages;
	private Long totalElements;

	@Getter
	public static class SingleCampaign{// 단일 캠페인
		private Long campaignId;
		private String title;
		private String writer;
		private String description;
		private String category;
		private Long joinCount;
		private Long joinMemberCount;
		private String date;
		private String campaignImageUrl;

		public SingleCampaign(Campaign campaign) {
			this.campaignId = campaign.getCampaignId();
			this.title = campaign.getTitle();
			this.writer = campaign.getWriter().getNickname();
			this.description = campaign.getDescription();
			this.date = campaign.getDate();
			this.campaignImageUrl = campaign.getCampaignimageUrl();
			this.joinCount = campaign.getTotalJoinCount();
			this.joinMemberCount = campaign.getJoinMemberCount();
		}



		public static CampaignResDto.SingleCampaign of(Campaign campaign) {
			return new CampaignResDto.SingleCampaign(campaign);
		}
	}

	public static CampaignResDto of(Page<Campaign> campaignPage) {
		Integer pageCount = campaignPage.getContent().size();
		return CampaignResDto.builder()
			.campaigns(campaignPage.getContent().stream()
				.map(SingleCampaign::of)
				.collect(Collectors.toList()))
			.totalPages(campaignPage.getTotalPages())
			.totalElements(campaignPage.getTotalElements())
			.count(pageCount)
			.build();
	}

	public static CampaignResDto of(List<Campaign> campaignList) {
		return CampaignResDto.builder()
			.campaigns(campaignList.stream().map(CampaignResDto.SingleCampaign::of).collect(Collectors.toList()))
			.count(campaignList.size())
			.build();
	}
}
