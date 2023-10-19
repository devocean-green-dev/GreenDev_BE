package com.devoceanyoung.greendev.domain.post.dto;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRecentResDto {
    private List<PostRecentResDto.SinglePost> posts;
    private Integer count;

    @Getter
    public static class SinglePost{// 단일 참여글
        private Long postId;
        private String content;
        private String postImageUrl;
        private LocalDateTime date;
        private String nickname;
        private String email;
        private String campaignTitle;
        private String profileImageUrl;

        public SinglePost(Post post) {
            Member writer = post.getWriter();
            Campaign campaign = post.getCampaign();
            this.postId = post.getPostId();
            this.content = post.getContent();
            this.postImageUrl = post.getPostImageUrl();
            this.date = post.getCreatedDate();
            this.nickname = writer.getNickname();
            this.email = writer.getEmail();
            this.campaignTitle = campaign.getTitle();
            this.profileImageUrl = writer.getProfileImageUrl();
        }

        public static PostRecentResDto.SinglePost of(Post post) {
            return new PostRecentResDto.SinglePost(post);
        }
    }

    public static PostRecentResDto of(List<Post> postList) {
        return PostRecentResDto.builder()
                .posts(postList.stream().map(PostRecentResDto.SinglePost::of).collect(Collectors.toList()))
                .count(postList.size())
                .build();
    }
}
