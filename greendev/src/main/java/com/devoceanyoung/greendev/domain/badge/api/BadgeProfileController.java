package com.devoceanyoung.greendev.domain.badge.api;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.devoceanyoung.greendev.domain.auth.AuthUser;

import com.devoceanyoung.greendev.domain.badge.domain.Profile;
import com.devoceanyoung.greendev.domain.badge.dto.ProfileUpdateReqDto;
import com.devoceanyoung.greendev.domain.badge.dto.ProfileResDto;
import com.devoceanyoung.greendev.domain.badge.service.BadgeInstanceService;;
import com.devoceanyoung.greendev.domain.badge.service.ProfileService;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.global.constant.StatusEnum;
import com.devoceanyoung.greendev.global.dto.StatusResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class BadgeProfileController {
	private final ProfileService profileService;
	private final BadgeInstanceService badgeInstanceService;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> readProfile(
		@AuthUser Member member) {
		Profile profile = profileService.findProfileByMember(member);
		ProfileResDto response = ProfileResDto.of(profile.getBadgeInstances());
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}

	@PatchMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> updateProfile(
		@AuthUser Member member,
		@RequestBody ProfileUpdateReqDto profileUpdateReqDto) {
		badgeInstanceService.updateBadgePosition(profileUpdateReqDto);
		Profile profile = profileService.findProfileByMember(member);
		ProfileResDto response = ProfileResDto.of(profile.getBadgeInstances());
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}


}
