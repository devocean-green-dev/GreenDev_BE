package com.devoceanyoung.greendev.domain.member.exception;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

public class MemberNotFoundException extends RuntimeException {
	public MemberNotFoundException() {
		super(MEMBER_NOT_FOUND);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
