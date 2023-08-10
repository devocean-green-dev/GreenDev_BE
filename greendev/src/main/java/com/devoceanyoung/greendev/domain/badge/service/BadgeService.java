package com.devoceanyoung.greendev.domain.badge.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.badge.domain.Badge;
import com.devoceanyoung.greendev.domain.badge.dto.BadgeReqDto;
import com.devoceanyoung.greendev.domain.badge.exception.BadgeNotFoundException;
import com.devoceanyoung.greendev.domain.badge.repository.BadgeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BadgeService {
	private final BadgeRepository badgeRepository;
	public Long create(BadgeReqDto reqDto){
		Badge createdBadge = badgeRepository.save(reqDto.toEntity());
		return createdBadge.getBadgeId();
	}

	@Transactional(readOnly = true)
	public Badge findByBadgeId(Long id){
		return badgeRepository.findByBadgeId(id).orElseThrow(BadgeNotFoundException::new);
	}

}
