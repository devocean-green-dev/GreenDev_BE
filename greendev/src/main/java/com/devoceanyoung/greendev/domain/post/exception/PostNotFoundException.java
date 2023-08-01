package com.devoceanyoung.greendev.domain.post.exception;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

public class PostNotFoundException extends RuntimeException{
	public PostNotFoundException() {
		super(POST_NOT_FOUND);

	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
