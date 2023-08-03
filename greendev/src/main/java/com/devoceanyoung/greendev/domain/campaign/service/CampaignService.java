package com.devoceanyoung.greendev.domain.campaign.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.campaign.dto.CampaignReqDto;
import com.devoceanyoung.greendev.domain.campaign.exception.CampaignNotFoundException;
import com.devoceanyoung.greendev.domain.campaign.repository.CampaignRepository;
import com.devoceanyoung.greendev.domain.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service//서비스 레이어, 내부에서 자바 로직을 처리함
@Transactional
@RequiredArgsConstructor
public class CampaignService {
	private final CampaignRepository campaignRepository;

	public Long create(Member member, CampaignReqDto reqDto){
		Campaign campaign = campaignRepository.save(reqDto.toEntity(member));
		return campaign.getCampaignId();
	}

	public void update(Long campaignId ,CampaignReqDto reqDto){
		Campaign campaign = findById(campaignId);
		campaign.updateContent(reqDto);
	}
	public void delete(Campaign campaign) {
		campaignRepository.delete(campaign);
	}

	@Transactional(readOnly = true)
	public Campaign findById(Long campaignId){
		return campaignRepository.findById(campaignId).orElseThrow(CampaignNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public Page<Campaign> findAll(Pageable pageable){
		return campaignRepository.findAll(pageable);
	}

}
