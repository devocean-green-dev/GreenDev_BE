package com.devoceanyoung.greendev.domain.member.controller;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

import java.util.List;

import javax.validation.Valid;

import com.devoceanyoung.greendev.domain.post.dto.PostRecentResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.devoceanyoung.greendev.domain.auth.AuthUser;
import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.campaign.dto.CampaignResDto;
import com.devoceanyoung.greendev.domain.campaign.service.ParticipationService;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.member.dto.MemberReqDto;
import com.devoceanyoung.greendev.domain.member.dto.MemberResDto;
import com.devoceanyoung.greendev.domain.member.service.MemberService;
import com.devoceanyoung.greendev.domain.post.domain.Post;
import com.devoceanyoung.greendev.domain.post.dto.PostCountResDto;
import com.devoceanyoung.greendev.domain.post.service.PostService;
import com.devoceanyoung.greendev.global.constant.StatusEnum;
import com.devoceanyoung.greendev.global.dto.StatusResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final ParticipationService participationService;
	private final PostService postService;

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> readMember(@AuthUser Member member) {
		MemberResDto response = MemberResDto.of(member);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}


	@PatchMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> updateNickname(@AuthUser Member member,
		@RequestBody @Valid final MemberReqDto reqDto) {
		memberService.update(member.getMemberId(), reqDto);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(NICKNAME_CHANGE_SUCCESS)
			.build());
	}

	@GetMapping("/campaigns")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> readMemberCampaignList(@AuthUser Member member)
	{
		List<Campaign> campaignList = participationService.getCampaignsParticipatedByMember(member);
		CampaignResDto response = CampaignResDto.of(campaignList);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}

	@GetMapping("/posts")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> readMemberPostList(@AuthUser Member member){
		List<Post> postList = postService.getRecentPostsByMember(member);
		PostRecentResDto response = PostRecentResDto.of(postList);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}

	@GetMapping("/grass")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> readMemberGrass(@AuthUser Member member){
		List<PostCountResDto> response = postService.countRecentPosts(member);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}


}
