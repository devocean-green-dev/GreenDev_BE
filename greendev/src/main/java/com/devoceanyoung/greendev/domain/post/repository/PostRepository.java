package com.devoceanyoung.greendev.domain.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findByCampaign(Campaign campaign, Pageable pageable);
	List<Post> findAllByCampaign(Campaign campaign);
}
