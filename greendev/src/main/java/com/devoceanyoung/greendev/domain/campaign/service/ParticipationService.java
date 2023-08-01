package com.devoceanyoung.greendev.domain.campaign.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;

import com.devoceanyoung.greendev.domain.campaign.domain.Participation;
import com.devoceanyoung.greendev.domain.campaign.repository.ParticipationRepository;
import com.devoceanyoung.greendev.domain.member.domain.Member;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service//서비스 레이어, 내부에서 자바 로직을 처리함
@Transactional
@RequiredArgsConstructor
public class ParticipationService {
	private final ParticipationRepository participationRepository;
	public void checkParticipation(Member member, Campaign campaign){
		Optional<Participation> existingParticipation = findByMemberAndCampaign(member, campaign);

		if (existingParticipation.isPresent()) {
			existingParticipation.get().increaseJoinCount();
		} else {
			Participation newParticipation = Participation.builder()
				.member(member)
				.campaign(campaign)
				.build();
			participationRepository.save(newParticipation);
		}

	}

	public List<Campaign> getCampaignsParticipatedByMember(Member member) {
		List<Participation> participations = findByMember(member);

		List<Campaign> campaigns = new ArrayList<>();
		for (Participation participation : participations) {
			campaigns.add(participation.getCampaign());
		}
		return campaigns;
	}
	@Transactional(readOnly = true)
	public Optional<Participation> findByMemberAndCampaign(Member member, Campaign campaign)
	{
		return participationRepository.findByMemberAndCampaign(member, campaign);
	}
	@Transactional(readOnly = true)
	public List<Participation> findByMember(Member member){
		return participationRepository.findByMember(member);
	}
}
