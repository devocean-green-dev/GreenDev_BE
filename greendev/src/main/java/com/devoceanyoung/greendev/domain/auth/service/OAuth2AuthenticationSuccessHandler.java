package com.devoceanyoung.greendev.domain.auth.service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.apache.bcel.generic.FieldGenOrMethodGen;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.devoceanyoung.greendev.domain.auth.domain.GoogleUserInfo;
import com.devoceanyoung.greendev.domain.auth.domain.KakaoUserInfo;
import com.devoceanyoung.greendev.domain.auth.domain.NaverUserInfo;
import com.devoceanyoung.greendev.domain.auth.domain.PrincipalDetails;
import com.devoceanyoung.greendev.domain.member.domain.ProviderType;
import com.devoceanyoung.greendev.global.jwt.JwtProvider;
import com.devoceanyoung.greendev.global.redis.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JwtProvider jwtProvider;
	private final RedisService redisService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken)authentication;

		ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

		String email = "null";
		if (providerType.equals(ProviderType.KAKAO)) {
			KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(attributes);
			email = kakaoUserInfo.getEmail();
			System.out.println("kakao");
			System.out.println(email);
		}
		else if(providerType.equals(ProviderType.NAVER)){
			NaverUserInfo naverUserInfo = new NaverUserInfo(attributes);
			email = naverUserInfo.getEmail();
		}
		else if(providerType.equals(ProviderType.GOOGLE)){
			GoogleUserInfo googleUserInfo = new GoogleUserInfo(attributes);
			email = googleUserInfo.getEmail();
		}
		else {
			Map<String, Object> providerData = (Map<String, Object>) attributes.get(providerType.name().toLowerCase());
			if (providerData != null) {
				email = (String) providerData.get("email");
			} else {
				// Handle the case where providerData is null
			}
		}


		String nextPageUrl = getNextPageUrl(request);

		String url = makeRedirectUrl(email, nextPageUrl, providerType);
		ResponseCookie responseCookie = generateRefreshTokenCookie(email);
		response.setHeader("Set-Cookie", responseCookie.toString());
		response.getWriter().write(url);

		if (response.isCommitted()) {
			logger.info("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
			return;
		}

		getRedirectStrategy().sendRedirect(request, response, url);
	}

	private String getNextPageUrl(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String nextPageUrl = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("nextPageUrl")) {
					nextPageUrl = cookie.getValue();
					break;
				}
			}
		}
		return nextPageUrl;
	}

	private String makeRedirectUrl(String email, String nextPageUrl, ProviderType providerType) {

		if (nextPageUrl == null) {
			nextPageUrl = "http://localhost:5500/";
		}

		String accessToken = jwtProvider.generateAccessToken(email);
		System.out.println(accessToken);

		return UriComponentsBuilder.fromHttpUrl(nextPageUrl)
			.queryParam("accessToken", accessToken)
			.queryParam("nextPageUrl", nextPageUrl)
			.build()
			.encode()
			.toUriString();
	}

	public ResponseCookie generateRefreshTokenCookie(String email) {
		String refreshToken = jwtProvider.generateRefreshToken(email);
		Long refreshTokenValidationMs = jwtProvider.getRefreshTokenValidationMs();

		redisService.setData("RefreshToken:" + email, refreshToken, refreshTokenValidationMs);

		return ResponseCookie.from("refreshToken", refreshToken)
			.path("/") // 해당 경로 하위의 페이지에서만 쿠키 접근 허용. 모든 경로에서 접근 허용한다.
			.domain("greendev.com")
			.maxAge(TimeUnit.MILLISECONDS.toSeconds(refreshTokenValidationMs)) // 쿠키 만료 시기(초). 없으면 브라우저 닫힐 때 제거
			.secure(true) // HTTPS로 통신할 때만 쿠키가 전송된다.
			.sameSite("none")
			.httpOnly(true) // JS를 통한 쿠키 접근을 막아, XSS 공격 등을 방어하기 위한 옵션이다.
			.build();
	}
}
