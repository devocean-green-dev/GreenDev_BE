package com.devoceanyoung.greendev.domain.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.member.domain.MemberAdapter;
import com.devoceanyoung.greendev.domain.member.repository.MemberRepository;
import com.devoceanyoung.greendev.global.exception.customException.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServcieImpl implements UserDetailsService {
	private final MemberRepository
		memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);
		Member member = memberRepository.findByEmail(email).orElseThrow(
			MemberNotFoundException::new);

		// UserDetails를 반환한다.
		return new MemberAdapter(member);
	}

}