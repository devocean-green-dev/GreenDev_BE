package com.devoceanyoung.greendev.domain.badge.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BadgeInstance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "badge_instance_id", updatable = false)
	private Long badgeInstanceId;

	private Integer position; // This is a number from 1 to 10

	@ManyToOne
	@JoinColumn(name = "badge_id")
	private Badge badge;

	@ManyToOne
	@JoinColumn(name = "profile_id")
	private Profile profile;

	@Builder
	public BadgeInstance(Integer position, Badge badge, Profile profile) {
		this.position = position;
		this.badge = badge;
		this.profile = profile;
	}

	public void updatePosition(Integer position) {
		this.position = position;
	}

	public void setProfile(Profile profile){
		this.profile = profile;
	}

}

