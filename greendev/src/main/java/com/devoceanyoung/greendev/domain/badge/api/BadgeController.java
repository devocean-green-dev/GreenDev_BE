package com.devoceanyoung.greendev.domain.badge.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devoceanyoung.greendev.domain.badge.domain.Badge;
import com.devoceanyoung.greendev.domain.badge.dto.BadgeReqDto;
import com.devoceanyoung.greendev.domain.badge.dto.BadgeResDto;
import com.devoceanyoung.greendev.domain.badge.service.BadgeService;
import com.devoceanyoung.greendev.global.constant.StatusEnum;
import com.devoceanyoung.greendev.global.dto.StatusResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/badges")
@RequiredArgsConstructor
public class BadgeController {
	private final BadgeService badgeService;

	@GetMapping("/{badgeId}")
	public ResponseEntity<StatusResponse> readBadge(@RequestParam Long badgeId){
		Badge badge = badgeService.findByBadgeId(badgeId);
		BadgeResDto response = new BadgeResDto(badge);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}


	@PostMapping
	public ResponseEntity<StatusResponse> createBadge(
		@RequestBody BadgeReqDto badgeReqDto) {
		Long badgeId = badgeService.create(badgeReqDto);
		Badge badge = badgeService.findByBadgeId(badgeId);
		BadgeResDto response = new BadgeResDto(badge);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(badgeId)
			.toUri();

		return ResponseEntity.created(location)
			.body(StatusResponse.builder()
				.status(StatusEnum.CREATED.getStatusCode())
				.message(StatusEnum.CREATED.getCode())
				.data(response)
				.build());
	}
}
