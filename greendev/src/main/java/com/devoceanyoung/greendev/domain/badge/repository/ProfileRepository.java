package com.devoceanyoung.greendev.domain.badge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devoceanyoung.greendev.domain.badge.domain.Profile;
import com.devoceanyoung.greendev.domain.member.domain.Member;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findByMember(Member member);
}
