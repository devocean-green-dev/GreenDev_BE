package com.devoceanyoung.greendev.domain.badge.exception;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

public class BadgeInstanceNotFoundException extends RuntimeException{
	public BadgeInstanceNotFoundException() {
		super(BADGE_INSTANCE_NOT_FOUND );
	}
}

