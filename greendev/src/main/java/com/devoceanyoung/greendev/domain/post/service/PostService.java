package com.devoceanyoung.greendev.domain.post.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.badge.service.BadgeInstanceService;
import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.campaign.service.CampaignService;
import com.devoceanyoung.greendev.domain.campaign.service.ParticipationService;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.post.domain.Post;
import com.devoceanyoung.greendev.domain.post.dto.PostCountResDto;
import com.devoceanyoung.greendev.domain.post.dto.PostReqDto;
import com.devoceanyoung.greendev.domain.post.exception.PostNotFoundException;
import com.devoceanyoung.greendev.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final ParticipationService participationService;
	private final CampaignService campaignService;
	private final BadgeInstanceService badgeService;

	public Long create(Member member, Long campaignId, PostReqDto postReqDto) {

		Campaign campaign = campaignService.findById(campaignId);
		Post post = postRepository.save(postReqDto.toEntity(member, campaign));
		campaign.addPost(post);
		participationService.checkParticipation(member, campaign);
		badgeService.checkAndAssignBadge(member);

		return post.getPostId();
	}

	public void update(Long postId, PostReqDto postReqDto) {
		Post post = findById(postId);
		post.updateContent(postReqDto);

	}
	public void delete(Long postId) {
		Post post = findById(postId);
		postRepository.delete(post);

	}

	public List<PostCountResDto> countRecentPosts(Member member) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime thirtyDaysAgo = now.minusDays(30);

		List<Object[]> postCounts = countPostsByMemberAndDateBetween(member, thirtyDaysAgo, now);

		// Map을 사용하여 DB에서 가져온 데이터 저장
		Map<String, Long> countMap = new HashMap<>();
		for (Object[] postCount : postCounts) {
			countMap.put(postCount[0].toString(), (Long) postCount[1]);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// 최근 30일 동안의 날짜 리스트 생성 및 PostCountResDto 리스트 생성
		List<PostCountResDto> result = IntStream.range(0, 30)
				.mapToObj(i -> now.minusDays(i).format(formatter))
				.map(date -> new PostCountResDto(date, countMap.getOrDefault(date, 0L)))
				.collect(Collectors.toList());

		return result;
	}


	@Transactional(readOnly = true)
	public Page<Post> getPostsByCampaign(Long campaignId, Pageable pageable) {
		Campaign campaign = campaignService.findById(campaignId);
		return postRepository.findByCampaign(campaign, pageable);
	}

	@Transactional(readOnly = true)
	public Post findById(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(PostNotFoundException::new);
	}

	@Transactional(readOnly = true)
	public List<Post> findByCampaign(Campaign campaign) {
		return postRepository.findAllByCampaign(campaign);
	}

	@Transactional(readOnly = true)
	public List<Post> getRecentPostsByMember(Member member) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime thirtyDaysAgo = now.minusDays(30);
		return postRepository.findByWriterAndDateBetween(member, thirtyDaysAgo, now);
	}

	@Transactional(readOnly = true)
	public List<Object[]> countPostsByMemberAndDateBetween(Member member, LocalDateTime startDate, LocalDateTime endDate){
		 return postRepository.countPostsByMemberAndDateBetween(member, startDate, endDate);
	}

	@Transactional(readOnly = true)
	public Integer countPostsByWriter(Member writer)
	{return postRepository.countAllByWriter(writer);}

}
