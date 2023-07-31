package com.devoceanyoung.greendev.domain.campaign.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
	Optional<Campaign> findById(Long campaignId);
	Page<Campaign> findAll(Pageable pageable);
}
