package com.devoceanyoung.greendev.domain.campaign.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import org.springframework.data.jpa.repository.Query;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
	Optional<Campaign> findById(Long campaignId);
	Page<Campaign> findAll(Pageable pageable);
	@Query("SELECT c FROM Campaign c WHERE "
			+ "LOWER(c.title) LIKE LOWER(CONCAT('%', :searchString, '%')) OR "
			+ "LOWER(c.category) LIKE LOWER(CONCAT('%', :searchString, '%')) OR "
			+ "LOWER(c.description) LIKE LOWER(CONCAT('%', :searchString, '%'))")
	Page<Campaign> searchCampaigns(String searchString, Pageable pageable);

	Page<Campaign> findByStartDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

	@Query("SELECT c FROM Campaign c WHERE "
			+ "(LOWER(c.title) LIKE LOWER(CONCAT('%', :searchString, '%')) OR "
			+ "LOWER(c.category) LIKE LOWER(CONCAT('%', :searchString, '%')) OR "
			+ "LOWER(c.description) LIKE LOWER(CONCAT('%', :searchString, '%'))) AND "
			+ "(c.startDate >= :startDate AND c.startDate <= :endDate OR :startDate IS NULL OR :endDate IS NULL)")
	Page<Campaign> searchCampaignsAndDateRange(String searchString, LocalDate startDate, LocalDate endDate, Pageable pageable);


}
