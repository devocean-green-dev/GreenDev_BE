package com.devoceanyoung.greendev.domain.campaign.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.global.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participation extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long participationId;

	@ManyToOne
	@JoinColumn(name = "campaign_id")
	private Campaign campaign;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	//참여 횟수
	private Integer joinCount;

	@Builder
	public Participation(Campaign campaign, Member member) {
		this.campaign = campaign;
		this.member = member;
		this.joinCount = 1;
	}

	public void increaseJoinCount() {
		this.joinCount += 1;
		this.campaign.increaseJoinCount();
	}
}
