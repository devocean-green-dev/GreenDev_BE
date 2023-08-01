package com.devoceanyoung.greendev.global.s3;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devoceanyoung.greendev.domain.image.exception.FileUploadFailException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Uploader {
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	private final AmazonS3 amazonS3Client;
	public String upload(final MultipartFile uploadImageFile, final String fileName) {
		putImageFileToS3(uploadImageFile, fileName);
		return amazonS3Client.getUrl(bucketName, fileName).toString();
	}

	private void putImageFileToS3(final MultipartFile uploadImageFile, final String fileName) {
		try {
			amazonS3Client.putObject(new PutObjectRequest(bucketName,
				fileName,
				uploadImageFile.getInputStream(),
				createObjectMetaData(uploadImageFile)));
		} catch (IOException e) {
			throw new FileUploadFailException();
		}
	}

	private ObjectMetadata createObjectMetaData(final MultipartFile uploadImageFile) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(uploadImageFile.getContentType());
		objectMetadata.setContentLength(uploadImageFile.getSize());
		objectMetadata.setCacheControl("31536000");
		return objectMetadata;
	}
}
