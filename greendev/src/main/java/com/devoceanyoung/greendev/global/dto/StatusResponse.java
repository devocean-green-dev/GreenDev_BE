package com.devoceanyoung.greendev.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponse {

	private Integer status;
	private String message;
	private Object data;


}
