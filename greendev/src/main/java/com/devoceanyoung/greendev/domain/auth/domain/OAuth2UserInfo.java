package com.devoceanyoung.greendev.domain.auth.domain;

import com.devoceanyoung.greendev.domain.member.domain.ProviderType;

public interface OAuth2UserInfo {
	String getProviderId();
	ProviderType getProvider();
	String getEmail();
	String getName();
	String getProfileImageUrl();
}