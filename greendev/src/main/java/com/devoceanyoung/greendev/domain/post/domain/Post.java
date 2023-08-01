package com.devoceanyoung.greendev.domain.post.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.post.dto.PostReqDto;
import com.devoceanyoung.greendev.global.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity//해당 클래스에 있는 내부변수에 모두 @Column을 내부적으로 포함 -> 옵셥없으면 생략 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어를 PROTECTED로 설정해놓게 되면 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단
@Getter
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;

	@Column(columnDefinition = "TEXT")//
	private String content;

	private String postImageUrl;
	private LocalDateTime date;

	@ManyToOne//(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member writer;

	@Builder
	public Post(String content, String postImageUrl, LocalDateTime date ,Campaign campaign, Member writer) {
		this.content = content;
		this.postImageUrl = postImageUrl;
		this.date = date;
		this.campaign = campaign;
		this.writer = writer;
	}

	public void updateContent(final PostReqDto postReqDto){
		this.content = postReqDto.getContent();
		this.date = postReqDto.getDate();
		this.postImageUrl = postReqDto.getPostImage();

	}
	public void setCampaign(Campaign campaign){
		this.campaign = campaign;
	}

}
