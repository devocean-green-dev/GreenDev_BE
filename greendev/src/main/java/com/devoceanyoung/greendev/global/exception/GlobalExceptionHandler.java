package com.devoceanyoung.greendev.global.exception;

import java.time.DateTimeException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devoceanyoung.greendev.domain.campaign.exception.CampaignNotFoundException;
import com.devoceanyoung.greendev.domain.image.exception.FileNameEmptyException;
import com.devoceanyoung.greendev.domain.image.exception.FileUploadFailException;
import com.devoceanyoung.greendev.domain.image.exception.UnsupportedImageExtension;
import com.devoceanyoung.greendev.domain.image.exception.UnsupportedImageFileTypeException;
import com.devoceanyoung.greendev.domain.post.exception.PostNotFoundException;
import com.devoceanyoung.greendev.domain.member.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	/*================== Basic Exception ==================*/
	@ExceptionHandler(RuntimeException.class)
	protected final ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException e) {
		final ErrorResponse response = ErrorResponse.builder()
			.status(HttpStatus.BAD_REQUEST)
			.code(ErrorCode.RUNTIME_EXCEPTION)
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@ExceptionHandler(DateTimeException.class)
	protected final ResponseEntity<ErrorResponse> handleDateTimeException(DateTimeException e) {
		final ErrorResponse response = ErrorResponse.builder()
			.status(HttpStatus.BAD_REQUEST)
			.code(ErrorCode.BAD_DATE_REQUEST)
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	/**
	 * 지원하지 않은 HTTP method 호출 할 경우 발생
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {
		log.error("handleHttpRequestMethodNotSupportedException", e);
		return ErrorResponse.toErrorResponseEntity(ErrorCode.METHOD_NOT_ALLOWED, e.getMessage());
	}

	// vaild 오류
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors()
			.forEach(c -> errors.put(((FieldError)c).getField(), c.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}

	/*================== Member Exception ==================*/


	// 존재하지 않는 유저
	@ExceptionHandler(MemberNotFoundException.class)
	protected final ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.MEMBER_NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(CampaignNotFoundException.class)
	protected final ResponseEntity<ErrorResponse> handleCampaignNotFoundException(CampaignNotFoundException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.CAMPAIGN_NOT_FOUND, e.getMessage());
	}
	@ExceptionHandler(PostNotFoundException.class)
	protected final ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.POST_NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(UnsupportedImageExtension.class)
	protected final ResponseEntity<ErrorResponse> handleUnsupportedImageExtension(UnsupportedImageExtension e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.IMAGE_UNSUPPORTED_EXTENSION, e.getMessage());
	}

	@ExceptionHandler(UnsupportedImageFileTypeException.class)
	protected final ResponseEntity<ErrorResponse> handleUnsupportedImageFileTypeException(UnsupportedImageFileTypeException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.IMAGE_UNSUPPORTED_FILETYPE, e.getMessage());
	}

	@ExceptionHandler(FileNameEmptyException.class)
	protected final ResponseEntity<ErrorResponse> handleFileNameEmptyException(FileNameEmptyException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.FILE_NAME_EMPTY, e.getMessage());
	}

	@ExceptionHandler(FileUploadFailException.class)
	protected final ResponseEntity<ErrorResponse> handleFileUploadFailException(FileUploadFailException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.FILE_UPLOAD_FAILURE, e.getMessage());
	}




}


