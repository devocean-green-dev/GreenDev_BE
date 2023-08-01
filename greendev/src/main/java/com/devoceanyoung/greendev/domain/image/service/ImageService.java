package com.devoceanyoung.greendev.domain.image.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devoceanyoung.greendev.domain.image.dto.ImageResDto;
import com.devoceanyoung.greendev.global.s3.FileNameGenerator;
import com.devoceanyoung.greendev.global.s3.S3Uploader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final S3Uploader s3Uploader;
	private final FileNameGenerator fileNameGenerator;

	public ImageResDto upload(final MultipartFile uploadImageFile) {
		validate(uploadImageFile);
		final String fileName = fileNameGenerator.generate(uploadImageFile.getOriginalFilename());
		final String uploadUrl = s3Uploader.upload(uploadImageFile, fileName);
		return new ImageResDto(uploadUrl, fileName);
	}

	private void validate(final MultipartFile uploadImageFile) {
		ImageFileValidator.checkEmptyFileName(uploadImageFile.getOriginalFilename());
		ImageFileValidator.checkImageType(uploadImageFile);
	}
}
