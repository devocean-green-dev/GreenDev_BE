package com.devoceanyoung.greendev.domain.auth.domain;

import java.util.Map;

import com.devoceanyoung.greendev.domain.member.domain.ProviderType;

public interface OAuth2UserInfo {
	String getProviderId();

	String getRegistrationId();

	Map<String, Object> getAttributes();

	ProviderType getProvider();
	String getEmail();
	String getNickname();
	String getProfileImageUrl();

	String getName();
}