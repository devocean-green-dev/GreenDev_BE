package com.devoceanyoung.greendev.domain.auth.service;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.auth.dto.LoginResDto;
import com.devoceanyoung.greendev.domain.auth.dto.TokenResDto;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.member.repository.MemberRepository;
import com.devoceanyoung.greendev.global.exception.customException.MemberNotFoundException;
import com.devoceanyoung.greendev.global.jwt.JwtProvider;
import com.devoceanyoung.greendev.global.redis.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	final private MemberRepository memberRepository;
	final private PasswordEncoder passwordEncoder;
	final private JwtProvider jwtProvider;
	final private RedisService redisService;


	private final AuthenticationManagerBuilder authenticationManagerBuilder;



	public UsernamePasswordAuthenticationToken getAuthenticationToken(String email, String password) {
		return new UsernamePasswordAuthenticationToken(email, password);
	}


}