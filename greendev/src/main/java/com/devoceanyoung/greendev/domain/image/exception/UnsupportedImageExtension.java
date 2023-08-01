package com.devoceanyoung.greendev.domain.image.exception;

public class UnsupportedImageExtension extends RuntimeException {

	public UnsupportedImageExtension(final String extension) {
		super(extension);
	}
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
