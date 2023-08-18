package com.devoceanyoung.greendev.domain.auth.domain;

import java.util.Map;

import com.devoceanyoung.greendev.domain.member.domain.ProviderType;

public class GithubUserInfo implements OAuth2UserInfo {

	public static final String REGISTRATION_ID = "github";

	private static final String PROVIDER_ID = "id";

	private static final String EMAIL = "email";
	private static final String PROFILE_IMAGE = "avatar_url";

	private static final String PREFIX = "https://api.github.com/users/";
	private static final String USER_URL = "url";
	private static final String LOGIN = "login";

	private Map<String, Object> attributes;

	public GithubUserInfo(Map<String, Object> attributes) {
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
		Object emailAttribute = attributes.get(EMAIL);
		if (emailAttribute != null) {
			return emailAttribute.toString();
		}

		Object loginAttribute = attributes.get(LOGIN);
		if (loginAttribute != null) {
			return generateTemporaryEmail(loginAttribute.toString());
		}

		return generateTemporaryEmail(getName());
	}

	private String generateTemporaryEmail(String nickname) {
		return nickname + "@" + REGISTRATION_ID + ".com"; // 이메일이 없는 경우 구분을 위해 임시 이메일 설정
	}


	@Override
	public String getProfileImageUrl() {
		return attributes.get(PROFILE_IMAGE).toString();
	}
	@Override
	public String getNickname() {
		String url = attributes.get(USER_URL).toString();
		if (url.startsWith(PREFIX)) {
			return url.substring(PREFIX.length());
		}
		return getName();
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
		return ProviderType.GITHUB;
	}
}
