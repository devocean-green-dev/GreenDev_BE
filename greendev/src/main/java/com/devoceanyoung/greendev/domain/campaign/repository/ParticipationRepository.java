package com.devoceanyoung.greendev.domain.campaign.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.campaign.domain.Participation;
import com.devoceanyoung.greendev.domain.member.domain.Member;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

	Optional<Participation> findById(Long participationId);
	Boolean existsByMemberAndCampaign(Member member, Campaign campaign);
	List<Participation> findByMember(Member member);
	Optional<Participation> findByMemberAndCampaign(Member member, Campaign campaign);

}
