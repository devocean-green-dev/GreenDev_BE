package com.devoceanyoung.greendev.domain.auth.service;

import static com.devoceanyoung.greendev.global.util.ConstantUtils.BASE_PROFILE_IMAGE;

import com.devoceanyoung.greendev.domain.auth.domain.PrincipalDetails;
import com.devoceanyoung.greendev.domain.auth.dto.AppLoginReqDto;
import com.devoceanyoung.greendev.domain.auth.dto.FirebaseAuthToken;
import com.devoceanyoung.greendev.domain.auth.dto.RefreshTokenResDto;
import com.devoceanyoung.greendev.domain.auth.dto.TokenResDto;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.member.domain.ProviderType;
import com.devoceanyoung.greendev.domain.member.domain.RoleType;
import com.devoceanyoung.greendev.domain.member.exception.MemberNotFoundException;
import com.devoceanyoung.greendev.domain.member.repository.MemberRepository;
import com.devoceanyoung.greendev.global.fireabse.FirebaseTokenFilter;
import com.google.firebase.auth.FirebaseToken;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoceanyoung.greendev.domain.auth.dto.AccessTokenDto;

import com.devoceanyoung.greendev.global.jwt.JwtProvider;
import com.devoceanyoung.greendev.global.redis.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

	private static final String REFRESH_TOKEN_PREFIX = "RefreshToken:";
	private static final String BLACKLIST_PREFIX = "BlackList:";
	private final JwtProvider jwtProvider;
	private final RedisService redisService;
	private final FirebaseTokenFilter fireBaseFilter;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final MemberRepository memberRepository;

	private final UserDetailsService userDetailsService;

	public AccessTokenDto signOut(AccessTokenDto requestDto) {
		// accessToken에서 Authentication 추출하기
		String accessToken = requestDto.getAccessToken();
		Authentication authentication = jwtProvider.getAuthentication(accessToken);

		// Redis의 RefreshToken을 가져오면서, 이미 로그아웃된 사용자인 경우 예외 처리
		String refreshToken = redisService.getRefreshToken(authentication.getName())
			.orElseThrow(() -> new RuntimeException("RefreshToken NotFound:" + authentication.getName()));

		// AccessToken의 남은 시간 추출 후 BlackList에 저장
		Long remainingTime = jwtProvider.getRemainingTime(accessToken);
		redisService.setData(BLACKLIST_PREFIX + accessToken, "signOut", remainingTime);
		redisService.deleteData(REFRESH_TOKEN_PREFIX + authentication.getName());

		return new AccessTokenDto(accessToken);
	}

	public AccessTokenDto refresh(String refreshToken) {
		// 들어온 refreshToekn 검증
		if (!jwtProvider.validateToken(refreshToken)) {
			log.error("유효하지 않은 토큰입니다. {}", refreshToken);
			throw new RuntimeException("Refresh Token 검증에 실패한 토큰입니다. : " + refreshToken);
		}
		// refreshToken에서 Authentication 추출하기
		Authentication authentication = jwtProvider.getAuthentication(refreshToken);

		// Redis의 RefreshToken을 가져오면서, 로그아웃된 사용자인 경우 예외 처리
		String findRefreshToken = redisService.getRefreshToken(authentication.getName())
			.orElseThrow(() -> new RuntimeException(authentication.getName()));

		// 저장되어있던 refreshToken과 일치하는지 확인
		if (!refreshToken.equals(findRefreshToken)) {
			log.error("저장된 토큰과 일치하지 않습니다. {} {}", refreshToken, findRefreshToken);
			throw new RuntimeException("Refresh Token 저장된 토큰과 일치하지 않습니다. : " + refreshToken);
		}

		// 토큰 생성을 위해 accessToken에서 Claims 추출
		String newAccessToken = jwtProvider.generateAccessToken(authentication.getName());

		return new AccessTokenDto(newAccessToken);
	}


	public UsernamePasswordAuthenticationToken getAuthenticationToken(String email, String password) {
		return new UsernamePasswordAuthenticationToken(email, password);
	}


	public RefreshTokenResDto getRefreshToken(AccessTokenDto accessTokenDto) {
		Authentication authentication = jwtProvider.getAuthentication(accessTokenDto.getAccessToken());

		// Redis의 RefreshToken을 가져오면서, 이미 로그아웃된 사용자인 경우 예외 처리
		String refreshToken = redisService.getRefreshToken(authentication.getName())
				.orElseThrow(() -> new RuntimeException("RefreshToken NotFound:" + authentication.getName()));
		Long remainingTime = jwtProvider.getRemainingTime(refreshToken);
		return new RefreshTokenResDto(refreshToken, remainingTime);
	}

    public TokenResDto firebaseLogin(FirebaseAuthToken firebaseAuthToken) {
		FirebaseToken firebaseToken = fireBaseFilter.verifyToken(firebaseAuthToken.getFirebaseAuthToken());
		if(!isExistedEmail(firebaseToken.getEmail())){
			firebaseSignUp(firebaseToken);
		}
		return generateToken(firebaseToken.getEmail());
    }

	private void firebaseSignUp(FirebaseToken firebaseToken) {
		String password = firebaseToken.getName() + "_"+ firebaseToken.getEmail();
		String username = "google" + "_" +firebaseToken.getUid();
		Member member = Member.builder()
				.nickname(firebaseToken.getName())
				.email(firebaseToken.getEmail())
				.password(password)
				.profileImageUrl(firebaseToken.getPicture())
				.username(username)
				.providerType(ProviderType.GOOGLE)
				.roleType(RoleType.USER)
				.build();
		memberRepository.save(member);
	}

	private static final Map<String, ProviderType> PROVIDER_TYPE_MAP = Map.of(
			"naver", ProviderType.NAVER,
			"kakao", ProviderType.KAKAO
	);
	private Member signUp(String provider, AppLoginReqDto appLoginReqDto) {
		String email = appLoginReqDto.getEmail();
		String nickname = Optional.ofNullable(appLoginReqDto.getNickname())
				.orElseGet(() -> email.substring(0, email.indexOf("@")));
		String profileImageUrl = Optional.ofNullable(appLoginReqDto.getProfileImageUrl())
				.orElse(BASE_PROFILE_IMAGE);
		String password = appLoginReqDto.getUsername() + "_" + email;
		ProviderType providerType = PROVIDER_TYPE_MAP.getOrDefault(provider, ProviderType.KAKAO);

		Member member = Member.builder()
				.nickname(nickname)
				.email(email)
				.password(password)
				.profileImageUrl(profileImageUrl)
				.username(appLoginReqDto.getUsername())
				.providerType(providerType)
				.roleType(RoleType.USER)
				.build();

		return memberRepository.save(member);
	}

	public TokenResDto login(String providerType, AppLoginReqDto appLoginReqDto) {
		if(!isExistedEmail(appLoginReqDto.getEmail())) {
			signUp(providerType, appLoginReqDto);
		}
		return generateToken(appLoginReqDto.getEmail());
	}

	private TokenResDto generateToken(String email) {
		Member member = findByEmail(email);

		UserDetails userDetails = new PrincipalDetails(member);
		Authentication authentication =  new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = jwtProvider.generateAccessToken(authentication.getName());
		String refreshToken = jwtProvider.generateRefreshToken(authentication.getName());
		Long refreshTokenValidationMs = jwtProvider.getRefreshTokenValidationMs();

		redisService.setData(REFRESH_TOKEN_PREFIX + authentication.getName(), refreshToken, refreshTokenValidationMs);

		return TokenResDto.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.refreshTokenRemaininTime(refreshTokenValidationMs)
				.build();
	}

	@Transactional(readOnly = true)
	public boolean isExistedEmail(String email){
		return memberRepository.existsByEmail(email);
	}

	@Transactional(readOnly = true)
	public Member findByEmail(String email){
		return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
	}
}