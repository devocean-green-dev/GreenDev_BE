package com.devoceanyoung.greendev.domain.post.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.campaign.dto.CampaignResDto;
import com.devoceanyoung.greendev.domain.post.domain.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResDto {
	private List<PostResDto.SinglePost> posts;
	private Integer count;
	private Integer totalPages;
	private Long totalElements;

	@Getter
	public static class SinglePost{// 단일 참여글
		private Long postId;
		private String content;
		private String postImageUrl;
		private LocalDateTime date;
		private String nickname;
		private String email;
		private String profileImageUrl;

		public SinglePost(Post post) {
			this.postId = post.getPostId();
			this.content = post.getContent();
			this.postImageUrl = post.getPostImageUrl();
			this.date = post.getCreatedDate();
			this.nickname = post.getWriter().getNickname();
			this.email = post.getWriter().getEmail();
			this.profileImageUrl = post.getWriter().getProfileImageUrl();
		}

		public static PostResDto.SinglePost of(Post post) {
			return new PostResDto.SinglePost(post);
		}
	}

	public static PostResDto of(Page<Post> postPage) {
		Integer pageCount = postPage.getContent().size();
		return PostResDto.builder()
			.posts(postPage.getContent().stream()
				.map(PostResDto.SinglePost::of)
				.collect(Collectors.toList()))
			.totalPages(postPage.getTotalPages())
			.totalElements(postPage.getTotalElements())
			.count(pageCount)
			.build();
	}
}
