package com.devoceanyoung.greendev.domain.image.exception;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

public class FileNameEmptyException extends RuntimeException {
	public FileNameEmptyException() {
		super(FILE_NAME_EMPTY);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
