package com.devoceanyoung.greendev.global.s3;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.apache.commons.io.FilenameUtils;

import com.devoceanyoung.greendev.domain.image.ImageExtension;
import com.devoceanyoung.greendev.domain.image.exception.UnsupportedImageExtension;

@Component
public class FileNameGenerator {

	public String generate(final String originalFilename) {
		final String fileName = createNewName();
		final String extension = getExtension(originalFilename);
		return fileName + "." + extension;
	}

	private String createNewName() {
		return UUID.randomUUID().toString();
	}

	private String getExtension(final String originalFilename) {
		final String extension = FilenameUtils.getExtension(originalFilename);
		if (!ImageExtension.isSupport(extension)) {
			throw new UnsupportedImageExtension(extension);
		}
		return extension;
	}
}
