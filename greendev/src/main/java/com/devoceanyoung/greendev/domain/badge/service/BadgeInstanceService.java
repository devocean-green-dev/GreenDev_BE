package com.devoceanyoung.greendev.domain.badge.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.badge.domain.Badge;
import com.devoceanyoung.greendev.domain.badge.domain.BadgeInstance;
import com.devoceanyoung.greendev.domain.badge.domain.Profile;
import com.devoceanyoung.greendev.domain.badge.dto.ProfileUpdateReqDto;
import com.devoceanyoung.greendev.domain.badge.exception.BadgeInstanceNotFoundException;
import com.devoceanyoung.greendev.domain.badge.repository.BadgeInstanceRepository;
import com.devoceanyoung.greendev.domain.badge.repository.BadgeRepository;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BadgeInstanceService {
	private final BadgeInstanceRepository badgeInstanceRepository;
	private final ProfileService profileService;
	private final BadgeRepository badgeRepository;

	private final PostRepository postRepository;

	public void checkAndAssignBadge(Member member) {
		int postCount = postRepository.countAllByWriter(member);
		Profile profile = profileService.findProfileByMember(member);

		if (postCount % 3 == 0) {
			Optional<Badge> badgeOptional = findBadgeByLevel(postCount);

			if (badgeOptional.isPresent()) {
				BadgeInstance newBadge = BadgeInstance.builder()
					.profile(profile)
					.badge(badgeOptional.get())
					.position(0)
					.build();
				profile.addBadgeInstance(badgeInstanceRepository.save(newBadge));
			}
		}
	}

	public void updateBadgePosition(ProfileUpdateReqDto reqDto) {
		for (ProfileUpdateReqDto.SingleBadge singleBadge : reqDto.getBadges()) {
			BadgeInstance badgeInstance = badgeInstanceRepository.findById(singleBadge.getBadgeInstanceId())
				.orElseThrow(BadgeInstanceNotFoundException::new);
			badgeInstance.updatePosition(singleBadge.getPosition());
		}
	}

	@Transactional(readOnly = true)
	public Optional<Badge> findBadgeByLevel(Integer level){
		return badgeRepository.findByLevel(level);
	}



}
