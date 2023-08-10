package com.devoceanyoung.greendev.domain.badge.exception;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

public class ProfileNotFoundException extends RuntimeException{
	public ProfileNotFoundException() {
		super(PROFILE_NOT_FOUND);
	}
}
