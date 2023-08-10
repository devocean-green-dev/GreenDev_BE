package com.devoceanyoung.greendev.domain.badge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devoceanyoung.greendev.domain.badge.domain.BadgeInstance;

public interface BadgeInstanceRepository extends JpaRepository<BadgeInstance, Long> {
}
