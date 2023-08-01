package com.devoceanyoung.greendev.domain.post.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findByCampaign(Campaign campaign, Pageable pageable);

	List<Post> findAllByCampaign(Campaign campaign);

	@Query("SELECT p FROM Post p WHERE p.writer = :member AND p.date BETWEEN :startDate AND :endDate")
	List<Post> findByWriterAndDateBetween(Member member, LocalDateTime startDate, LocalDateTime endDate);
	@Query("SELECT function('DATE', p.date) as date, COUNT(p) as count FROM Post p WHERE p.writer = :member AND p.date BETWEEN :startDate AND :endDate GROUP BY function('DATE', p.date)")
	List<Object[]> countPostsByMemberAndDateBetween(Member member, LocalDateTime startDate, LocalDateTime endDate);

}