package com.devoceanyoung.greendev.domain.auth.api;

import static org.springframework.http.HttpHeaders.*;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.devoceanyoung.greendev.domain.auth.AuthUser;
import com.devoceanyoung.greendev.domain.auth.dto.LoginResDto;
import com.devoceanyoung.greendev.domain.auth.dto.TokenResDto;
import com.devoceanyoung.greendev.domain.auth.service.AuthService;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.member.domain.RoleType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//http://localhost:8880/login/oauth2/code/naver

@Slf4j
@RestController
@RequestMapping("/login/oauth/code")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String clientSecret;

	@GetMapping("/naver")
	@ResponseBody
	public ResponseEntity<LoginResDto> naverOAuthRedirect(@RequestParam String code, @RequestParam String state) {

		RestTemplate rt = new RestTemplate();

		HttpHeaders accessTokenHeaders = new HttpHeaders();
		accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");

		MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
		accessTokenParams.add("grant_type", "authorization_code");
		accessTokenParams.add("client_id", clientId);
		accessTokenParams.add("client_secret", clientSecret);
		accessTokenParams.add("code" , code);	// 응답으로 받은 코드
		accessTokenParams.add("state" , state); // 응답으로 받은 상태

		HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, accessTokenHeaders);

		ResponseEntity<String> accessTokenResponse = rt.exchange(
			"https://nid.naver.com/oauth2.0/token",
			HttpMethod.POST,
			accessTokenRequest,
			String.class
		);
		return ResponseEntity.ok()
			//.header(SET_COOKIE, responseCookie.toString())
			.body(new LoginResDto("test", accessTokenResponse.getBody()));

	}
	@GetMapping("/kakao")
	@ResponseBody
	public ResponseEntity<LoginResDto> kakaoOAuthRedirect(@RequestParam String code, @RequestParam String state) {
		// state는 무시하겠습니다.
		RestTemplate rt = new RestTemplate();

		HttpHeaders accessTokenHeaders = new HttpHeaders();
		accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");

		MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
		accessTokenParams.add("grant_type", "authorization_code");
		accessTokenParams.add("client_id", clientId);
		accessTokenParams.add("client_secret", clientSecret);
		accessTokenParams.add("code" , code);	// 응답으로 받은 코드
		accessTokenParams.add("state" , state); // 응답으로 받은 상태

		HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, accessTokenHeaders);

		ResponseEntity<String> accessTokenResponse = rt.exchange(
			"https://nid.naver.com/oauth2.0/token",
			HttpMethod.POST,
			accessTokenRequest,
			String.class
		);
		return ResponseEntity.ok()
			//.header(SET_COOKIE, responseCookie.toString())
			.body(new LoginResDto("test", accessTokenResponse.getBody()));

	}

	@GetMapping("/test")
	@ResponseBody
	public ResponseEntity<String> test(@AuthUser Member member){
		return ResponseEntity.ok().body(member.getEmail());

	}
}
