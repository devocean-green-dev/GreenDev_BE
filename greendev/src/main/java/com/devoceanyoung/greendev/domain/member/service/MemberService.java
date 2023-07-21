package com.devoceanyoung.greendev.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.member.repository.MemberRepository;
import com.devoceanyoung.greendev.global.exception.customException.MemberNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	final private MemberRepository memberRepository;
	@Transactional(readOnly = true)
	public boolean isExistedEmail(String email){
		return memberRepository.existsByEmail(email);
	}

	@Transactional(readOnly = true)
	public String findNicknameByEmail(String email){
		Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
		return member.getNickname();
	}

	@Transactional(readOnly = true)
	public Member findByEmail(String email){
		return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
	}
}
