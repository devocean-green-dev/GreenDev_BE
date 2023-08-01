package com.devoceanyoung.greendev.domain.image.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devoceanyoung.greendev.domain.image.dto.ImageResDto;
import com.devoceanyoung.greendev.domain.image.service.ImageService;
import com.devoceanyoung.greendev.global.constant.StatusEnum;
import com.devoceanyoung.greendev.global.dto.StatusResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
	private final ImageService imageService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<StatusResponse> uploadImage(@RequestPart("file") MultipartFile uploadImageFile) {
		ImageResDto response = imageService.upload(uploadImageFile);
		return ResponseEntity.ok(StatusResponse.builder()
			.status(StatusEnum.CREATED.getStatusCode())
			.message(StatusEnum.CREATED.getCode())
			.data(response)
			.build());
	}
}
