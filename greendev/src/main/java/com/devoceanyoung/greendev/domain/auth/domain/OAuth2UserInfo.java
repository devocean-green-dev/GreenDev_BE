package com.devoceanyoung.greendev.domain.auth.domain;

public interface OAuth2UserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
	String getProfileImageUrl();
}