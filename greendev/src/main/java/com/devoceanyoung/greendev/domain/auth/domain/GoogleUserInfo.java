package com.devoceanyoung.greendev.domain.auth.domain;
import java.security.Provider;
import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.devoceanyoung.greendev.domain.member.domain.ProviderType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GoogleUserInfo implements OAuth2UserInfo {

	private final Map<String, Object> attributes;

	public GoogleUserInfo(OAuth2User oAuth2User) {
		this.attributes = oAuth2User.getAttributes();
	}

	@Override
	public String getProviderId() {
		return (String) attributes.get("sub");
	}

	@Override
	public ProviderType getProvider() {
		return ProviderType.GOOGLE;
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}
	@Override
	public String getProfileImageUrl(){
		return (String) attributes.get("picture");
	}


}