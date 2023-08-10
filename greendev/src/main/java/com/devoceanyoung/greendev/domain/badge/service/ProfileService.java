package com.devoceanyoung.greendev.domain.badge.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.badge.domain.Profile;
import com.devoceanyoung.greendev.domain.badge.exception.ProfileNotFoundException;
import com.devoceanyoung.greendev.domain.badge.repository.ProfileRepository;
import com.devoceanyoung.greendev.domain.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {
	private final ProfileRepository profileRepository;

	@Transactional(readOnly = true)
	public Profile findProfileByMember(Member member){
		return profileRepository.findByMember(member).orElseThrow(ProfileNotFoundException::new);
	}
}
