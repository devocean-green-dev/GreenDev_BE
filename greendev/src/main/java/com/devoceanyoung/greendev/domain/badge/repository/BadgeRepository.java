package com.devoceanyoung.greendev.domain.badge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devoceanyoung.greendev.domain.badge.domain.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
	Optional<Badge> findByLevel(Integer level);

	Optional<Badge> findByBadgeId(Long id);
}
