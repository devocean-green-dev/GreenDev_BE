package com.devoceanyoung.greendev.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	/* Common */
	// Basic - C0***
	RUNTIME_EXCEPTION(BAD_REQUEST, "C0001", "RUNTIME_EXCEPTION"),
	METHOD_NOT_ALLOWED(BAD_REQUEST, "C0002", "METHOD_NOT_ALLOWED"),

	// CAMPAIGN - C1***

	CAMPAIGN_NOT_FOUND(NOT_FOUND, "C1002", "CAMPAIGN_NOT_FOUND"),
	BAD_DATE_REQUEST(BAD_REQUEST, "C1003", "BAD_DATE_REQUEST"),

	// Image - C11**
	IMAGE_MISSING(BAD_REQUEST, "C1101", "IMAGE_MISSING"),
	IMAGE_NOT_FOUND(NOT_FOUND, "C1102", "IMAGE_NOT_FOUND"),
	IMAGE_SIZE_CHECK_FAILURE(INTERNAL_SERVER_ERROR, "C1103", "IMAGE_SIZE_CHECK_FAILURE"),

	// User - C2***
	NICKNAME_DUPLICATE(CONFLICT, "C2002", "DUPLICATE_NICKNAME_EXISTS"),
	MEMBER_NOT_FOUND(NOT_FOUND, "C2003", "MEMBER_NOT_FOUND"),



	// TOKEN - E1***
	TOKEN_VALIDATE_FAILURE(BAD_REQUEST, "E1001", "INVALID_TOKEN"),
	REFRESHTOKEN_NOT_FOUND(BAD_REQUEST, "E1002", "REFRESHTOKEN_NOT_FOUND"),

	//SUCCESS
	LOGOUT_SUCCESS(OK, "S0001", "LOGOUT_SUCCESS");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
