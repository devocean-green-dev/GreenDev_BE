package com.devoceanyoung.greendev.domain.auth.domain;
import java.security.Provider;
import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.devoceanyoung.greendev.domain.member.domain.ProviderType;

import lombok.AllArgsConstructor;

public class GoogleUserInfo implements OAuth2UserInfo {

	public static final String REGISTRATION_ID = "google";

	private static final String PROVIDER_ID = "sub";

	private static final String EMAIL = "email";
	private static final String PROFILE = "picture";

	private Map<String, Object> attributes;

	public GoogleUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return attributes.get(PROVIDER_ID).toString();
	}

	@Override
	public String getRegistrationId() {
		return REGISTRATION_ID;
	}

	@Override
	public String getEmail() {
		return attributes.get(EMAIL).toString();
	}

	@Override
	public String getNickname() {
		String email = getEmail();
		return email.substring(0, email.indexOf("@"));
	}

	@Override
	public String getName() {
		return REGISTRATION_ID + "_" + getProviderId();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public ProviderType getProvider() {
		return ProviderType.GOOGLE;
	}

	@Override
	public String getProfileImageUrl(){
		return attributes.get(PROFILE).toString();
	}


}