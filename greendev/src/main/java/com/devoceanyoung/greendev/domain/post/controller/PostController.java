package com.devoceanyoung.greendev.domain.post.controller;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devoceanyoung.greendev.domain.auth.AuthUser;
import com.devoceanyoung.greendev.domain.campaign.service.CampaignService;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.post.domain.Post;
import com.devoceanyoung.greendev.domain.post.dto.PostReqDto;
import com.devoceanyoung.greendev.domain.post.dto.PostResDto;
import com.devoceanyoung.greendev.domain.post.service.PostService;
import com.devoceanyoung.greendev.global.constant.StatusEnum;
import com.devoceanyoung.greendev.global.dto.StatusResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	private final CampaignService campaignService;



	@GetMapping("/{postId}")
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<StatusResponse> readPost(
		@PathVariable Long postId) {
		Post post = postService.findById(postId);
		PostResDto.SinglePost response = PostResDto.SinglePost.of(post);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}

	@PatchMapping("/{postId}")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username ) or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<StatusResponse> updatePost(
		@PathVariable Long postId,
		@RequestBody PostReqDto postReqDto) {
		postService.update(postId, postReqDto);
		Post post = postService.findById(postId);
		PostResDto.SinglePost response = PostResDto.SinglePost.of(post);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}

	@DeleteMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username ) or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<StatusResponse> deleteCampaign(
		@PathVariable Long postId) {
		postService.delete(postId);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(POST_DELETE_SUCCESS)
			.build());
	}


}
