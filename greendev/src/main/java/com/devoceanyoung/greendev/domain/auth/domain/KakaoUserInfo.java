package com.devoceanyoung.greendev.domain.auth.domain;

import java.util.Map;

import com.devoceanyoung.greendev.domain.member.domain.ProviderType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo{

	private Map<String, Object> attributes;


	@Override
	public String getProviderId() {
		// Long 타입이기 때문에 toString으로 변호나
		return attributes.get("id").toString();
	}

	@Override
	public ProviderType getProvider() {
		return ProviderType.KAKAO;
	}

	@Override
	public String getEmail() {
		// kakao_account라는 Map에서 추출
		//return (String) ((Map) attributes.get("kakao_account")).get("email");
		//return (String) attributes.get("email");
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		return (String) kakaoAccount.get("email");
	}

	@Override
	public String getName() {
		// kakao_account라는 Map에서 추출
		//return (String) ((Map) attributes.get("profile")).get("nickname");
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
		return (String) kakaoProfile.get("nickname");
	}

	@Override
	public String getProfileImageUrl(){

		//return (String) ((Map) attributes.get("profile")).get("profile_image_url");
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
		return (String) kakaoProfile.get("profile_image_url");
	}
}