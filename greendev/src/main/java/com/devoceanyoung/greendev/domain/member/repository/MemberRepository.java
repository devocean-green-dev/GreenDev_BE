package com.devoceanyoung.greendev.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.devoceanyoung.greendev.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<Member> findByUsername(String username);
}
