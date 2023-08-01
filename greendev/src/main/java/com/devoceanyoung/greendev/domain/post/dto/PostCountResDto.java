package com.devoceanyoung.greendev.domain.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCountResDto {
	private String date;
	private Long count;

	public PostCountResDto(String date, Long count) {
		this.date = date;
		this.count = count;
	}
}
