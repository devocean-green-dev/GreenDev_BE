package com.devoceanyoung.greendev.domain.auth.domain;

import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo{

	private Map<String, Object> attributes;

	@Override
	public String getProviderId() {
		return (String) attributes.get("id");
	}

	@Override
	public String getProvider() {
		return "naver";
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getName() {
		return (String) ((Map) attributes.get("profile")).get("nickname");
	}
	@Override
	public String getProfileImageUrl(){
		return (String) ((Map) attributes.get("profile")).get("profile_image");
	}


}
