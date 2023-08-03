package com.devoceanyoung.greendev.domain.campaign.controller;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
	@ResponseStatus(value = HttpStatus.OK)
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


	@GetMapping("/{campaignId}")
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(value = HttpStatus.OK)
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
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<StatusResponse> createCampaign(
		@AuthUser Member member,
		@RequestBody CampaignReqDto campaignReqDto) {
		Long campaignId = campaignService.create(member, campaignReqDto);
		Campaign campaign = campaignService.findById(campaignId);
		CampaignResDto.SingleCampaign response = CampaignResDto.SingleCampaign.of(campaign);
		return ResponseEntity.ok(StatusResponse.builder()
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


}
