package com.devoceanyoung.greendev.domain.post.api;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devoceanyoung.greendev.domain.auth.AuthUser;
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
@RequestMapping("/api/v1/campaigns/{campaignId}")
@RequiredArgsConstructor
public class CampaignPostController {
	private final PostService postService;

	@GetMapping("/posts")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> readCampaignList(
		@PathVariable final Long campaignId,
		Pageable pageable) {
		Page<Post> postPage = postService.getPostsByCampaign(campaignId, pageable);
		PostResDto response = PostResDto.of(postPage);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}

	@PostMapping("/posts")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StatusResponse> createPost(
		@AuthUser Member member,
		@PathVariable Long campaignId,
		@RequestBody PostReqDto postReqDto) {
		Long postId = postService.create(member, campaignId , postReqDto);
		Post post = postService.findById(postId);
		PostResDto.SinglePost response = PostResDto.SinglePost.of(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(postId)
			.toUri();

		return ResponseEntity.created(location)
			.body(StatusResponse.builder()
				.status(StatusEnum.CREATED.getStatusCode())
				.message(StatusEnum.CREATED.getCode())
				.data(response)
				.build());
	}
}
