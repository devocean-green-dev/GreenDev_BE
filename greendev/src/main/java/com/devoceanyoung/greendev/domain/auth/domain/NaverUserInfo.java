package com.devoceanyoung.greendev.domain.auth.domain;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.devoceanyoung.greendev.domain.member.domain.ProviderType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo{

	private final Map<String, Object> attributes;

	public NaverUserInfo(OAuth2User oAuth2User) {
		this.attributes = oAuth2User.getAttribute("response");
	}

	@Override
	public String getProviderId() {
		return (String) attributes.get("id");
	}

	@Override
	public ProviderType getProvider() {
		return ProviderType.NAVER;
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getName() {
		return (String) attributes.get("nickname");
	}
	@Override
	public String getProfileImageUrl(){
		return (String) attributes.get("profile_image");
	}


}
