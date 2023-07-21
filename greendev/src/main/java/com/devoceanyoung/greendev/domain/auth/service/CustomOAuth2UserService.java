package com.devoceanyoung.greendev.domain.auth.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.auth.domain.GoogleUserInfo;
import com.devoceanyoung.greendev.domain.auth.domain.KakaoUserInfo;
import com.devoceanyoung.greendev.domain.auth.domain.NaverUserInfo;
import com.devoceanyoung.greendev.domain.auth.domain.OAuth2UserInfo;
import com.devoceanyoung.greendev.domain.auth.domain.PrincipalDetails;
import com.devoceanyoung.greendev.domain.auth.dto.OAuthAttributes;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.member.domain.RoleType;
import com.devoceanyoung.greendev.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final MemberRepository memberRepository;


	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("getAttributes : {}", oAuth2User.getAttributes());

		OAuth2UserInfo oAuth2UserInfo = null;

		String provider = userRequest.getClientRegistration().getRegistrationId();
		System.out.println(provider);

		if(provider.equals("google")) {
			log.info("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo( oAuth2User.getAttributes() );
		} else if(provider.equals("kakao")) {
			log.info("카카오 로그인 요청");
			oAuth2UserInfo = new KakaoUserInfo( (Map)oAuth2User.getAttributes() );
		} else if(provider.equals("naver")) {
			log.info("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo( (Map)oAuth2User.getAttributes().get("response") );
		}

		Member member = saveOrUpdate(oAuth2UserInfo);
		return new PrincipalDetails(member, oAuth2User.getAttributes());

	}

	// 혹시 이미 저장된 정보라면, update 처리
	private Member saveOrUpdate(OAuth2UserInfo oAuth2UserInfo) {
		String username = oAuth2UserInfo.getProvider().toString() + "_" + oAuth2UserInfo.getProviderId();
		String password = oAuth2UserInfo.getName() + "_"+ oAuth2UserInfo.getEmail();
		//String encodedPassword = passwordEncoder.encode(password);
		String encodedPassword = password;
		Member member = memberRepository.findByEmail(oAuth2UserInfo.getEmail())
			.map(entity -> {
				entity.updateMember(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getName(), oAuth2UserInfo.getProfileImageUrl());
				return entity;
			})
			.orElseGet(() ->
				Member.builder()
					.nickname(oAuth2UserInfo.getName())
					.email(oAuth2UserInfo.getEmail())
					.password(encodedPassword)
					.profileImageUrl(oAuth2UserInfo.getProfileImageUrl())
					.username(username)
					.providerType(oAuth2UserInfo.getProvider())
					.roleType(RoleType.USER)
					.build()
			);

		return memberRepository.save(member);

	}


}
