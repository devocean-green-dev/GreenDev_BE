package com.devoceanyoung.greendev.domain.image.exception;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

public class FileUploadFailException extends RuntimeException {
	public FileUploadFailException() {
		super(FILE_UPLOAD_FAILURE);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}