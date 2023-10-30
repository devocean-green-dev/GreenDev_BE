package com.devoceanyoung.greendev.domain.campaign.api;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

import java.net.URI;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devoceanyoung.greendev.domain.auth.AuthUser;
import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.campaign.dto.CampaignReqDto;
import com.devoceanyoung.greendev.domain.campaign.dto.CampaignResDto;
import com.devoceanyoung.greendev.domain.campaign.service.CampaignService;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.post.service.CampaignPostService;
import com.devoceanyoung.greendev.global.constant.StatusEnum;
import com.devoceanyoung.greendev.global.dto.StatusResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/campaigns")
@RequiredArgsConstructor
public class CampaignController {
	private final CampaignService campaignService;
	private final CampaignPostService campaignPostService;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> readCampaignList(
		Pageable pageable) {
		Page<Campaign> campaignList = campaignService.findAll(pageable);
		CampaignResDto response = CampaignResDto.of(campaignList);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}

	@GetMapping("/dates")
	public ResponseEntity<StatusResponse> readDateFilteredCampaignList(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			Pageable pageable) {
		Page<Campaign> campaignList = campaignService.findCampaignsByDateRange(startDate, endDate, pageable);
		CampaignResDto response = CampaignResDto.of(campaignList);
		return ResponseEntity.ok(StatusResponse.builder()
				.status(StatusEnum.OK.getStatusCode())
				.message(StatusEnum.OK.getCode())
				.data(response)
				.build());
	}

	@GetMapping("/{campaignId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> readCampaign(
		@PathVariable Long campaignId) {
		Campaign campaign = campaignService.findById(campaignId);
		CampaignResDto.SingleCampaign response = CampaignResDto.SingleCampaign.of(campaign);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> createCampaign(
		@AuthUser Member member,
		@RequestBody CampaignReqDto campaignReqDto) {
		Long campaignId = campaignService.create(member, campaignReqDto);
		Campaign campaign = campaignService.findById(campaignId);
		CampaignResDto.SingleCampaign response = CampaignResDto.SingleCampaign.of(campaign);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(campaign.getCampaignId())
			.toUri();

		return ResponseEntity.created(location)
			.body(StatusResponse.builder()
				.status(StatusEnum.CREATED.getStatusCode())
				.message(StatusEnum.CREATED.getCode())
				.data(response)
				.build());
	}

	@PatchMapping("/{campaignId}")
	@ResponseStatus(value = HttpStatus.OK)
	@PreAuthorize("isAuthenticated() and (( @campaignService.findById(#campaignId).getWriter().getEmail() == principal.username ) or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<StatusResponse> updateCampaign(
		@PathVariable Long campaignId,
		@RequestBody CampaignReqDto campaignReqDto) {
		campaignService.update(campaignId, campaignReqDto);
		Campaign campaign = campaignService.findById(campaignId);
		CampaignResDto.SingleCampaign response = CampaignResDto.SingleCampaign.of(campaign);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}

	@DeleteMapping("/{campaignId}")
	@ResponseStatus(value = HttpStatus.OK)
	@PreAuthorize("isAuthenticated() and (( @campaignService.findById(#campaignId).getWriter().getEmail() == principal.username ) or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<StatusResponse> deleteCampaign(
		@PathVariable Long campaignId) {
		Campaign campaign = campaignPostService.deleteCampaignWithPosts(campaignId);
		campaignService.delete(campaign);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(CAMPAIGN_DELETE_SUCCESS)
			.build());
	}

	@GetMapping("/search")
	public ResponseEntity<StatusResponse> searchCampaignList(
			@RequestParam String content,
			Pageable pageable) {
		Page<Campaign> campaignList = campaignService.searchCampaign(content, pageable);
		CampaignResDto response = CampaignResDto.of(campaignList);
		return ResponseEntity.ok(StatusResponse.builder()
				.status(StatusEnum.OK.getStatusCode())
				.message(StatusEnum.OK.getCode())
				.data(response)
				.build());
	}

	@GetMapping("/search/dates")
	public ResponseEntity<StatusResponse> searchDateFilteredCampaignList(
			@RequestParam(required = false) String content,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			Pageable pageable) {
		Page<Campaign> campaignList = campaignService.searchDateFilteredCampaign(content, startDate, endDate, pageable);
		CampaignResDto response = CampaignResDto.of(campaignList);
		return ResponseEntity.ok(StatusResponse.builder()
				.status(StatusEnum.OK.getStatusCode())
				.message(StatusEnum.OK.getCode())
				.data(response)
				.build());
	}


}
