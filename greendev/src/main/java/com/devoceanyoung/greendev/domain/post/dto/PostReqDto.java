package com.devoceanyoung.greendev.domain.post.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.post.domain.Post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReqDto {
	@NotBlank(message = "내용은 필수로 입력되어야 합니다.")
	private String content;

	private String postImage;
	private LocalDateTime date;

	@Builder
	public PostReqDto(String content, String postImage, LocalDateTime date) {
		this.content = content;
		this.postImage = postImage;
		this.date = date;
	}

	public Post toEntity(Member member, Campaign campaign){
		return Post.builder()
			.content(content)
			.date(date)
			.postImageUrl(postImage)
			.writer(member)
			.campaign(campaign)
			.build();
	}

}
