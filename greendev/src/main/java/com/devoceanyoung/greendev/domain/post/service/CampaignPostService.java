package com.devoceanyoung.greendev.domain.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.campaign.domain.Campaign;
import com.devoceanyoung.greendev.domain.campaign.exception.CampaignNotFoundException;
import com.devoceanyoung.greendev.domain.campaign.repository.CampaignRepository;
import com.devoceanyoung.greendev.domain.post.domain.Post;
import com.devoceanyoung.greendev.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CampaignPostService {

	private final CampaignRepository campaignRepository;
	private final PostRepository postRepository;

	public Campaign deleteCampaignWithPosts(Long campaignId){
		Campaign campaign = campaignRepository.findById(campaignId)
			.orElseThrow(CampaignNotFoundException::new);

		List<Post> posts = postRepository.findAllByCampaign(campaign);
		for (Post post : posts) {
			post.setCampaign(null);
			postRepository.save(post);
		}
		return campaign;
	}

}
