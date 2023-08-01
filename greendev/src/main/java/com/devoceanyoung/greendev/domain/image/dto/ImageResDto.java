package com.devoceanyoung.greendev.domain.image.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageResDto {
	private String uploadFileName;
	private String uploadFileUrl;

	@Builder
	public ImageResDto(final String url, final String fileName) {
		this.uploadFileName = fileName;
		this.uploadFileUrl = url;
	}
}
