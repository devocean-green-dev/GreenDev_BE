package com.devoceanyoung.greendev.domain.badge.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.devoceanyoung.greendev.domain.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id", updatable = false)
	private Long profileId;

	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "profile")
	private List<BadgeInstance> badgeInstances = new ArrayList<>();

	@Builder
	public Profile(Member member) {
		this.member = member;
	}

	public void addBadgeInstance(BadgeInstance badgeInstance){
		this.badgeInstances.add(badgeInstance);
	}
}
