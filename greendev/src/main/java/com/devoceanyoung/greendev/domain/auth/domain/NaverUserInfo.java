package com.devoceanyoung.greendev.domain.auth.domain;


import java.util.Map;



import com.devoceanyoung.greendev.domain.member.domain.ProviderType;


public class NaverUserInfo implements OAuth2UserInfo{
	public static final String REGISTRATION_ID = "naver";

	private static final String PROVIDER_ID = "id";
	private static final String EMAIL = "email";
	private static final String NICKNAME = "name";
	private static final String PROFILE = "profile_image";

	private final Map<String, Object> attributes;
	public NaverUserInfo(Map<String, Object> attributes) {
		this.attributes = (Map<String, Object>) attributes.get("response");
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
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public ProviderType getProvider() {
		return ProviderType.NAVER;
	}

	@Override
	public String getEmail() {
		return attributes.get("email").toString();
	}

	@Override
	public String getName() {
		return REGISTRATION_ID + "_" + this.getProviderId();
	}


	public String getNickname() {
		return attributes.get(NICKNAME).toString();
	}
	@Override
	public String getProfileImageUrl(){
		return attributes.get(PROFILE).toString();
	}


}
