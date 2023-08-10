package com.devoceanyoung.greendev.domain.badge.exception;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

public class BadgeNotFoundException extends RuntimeException{
	public BadgeNotFoundException() {
		super(BADGE_NOT_FOUND);
	}
}
