package com.devoceanyoung.greendev.domain.image.exception;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

public class UnsupportedImageFileTypeException extends RuntimeException {
	public UnsupportedImageFileTypeException() {
		super(IMAGE_UNSUPPORTED_FILETYPE);
	}
	public UnsupportedImageFileTypeException(final String extension) {
		super(IMAGE_UNSUPPORTED_FILETYPE + String.format("[%s]", extension));
	}
	@Override
	public String getMessage() {
		return super.getMessage();
	}

}