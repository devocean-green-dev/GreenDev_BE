package com.devoceanyoung.greendev.domain.member.controller;

import static com.devoceanyoung.greendev.global.constant.ResponseConstant.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devoceanyoung.greendev.domain.auth.AuthUser;
import com.devoceanyoung.greendev.domain.member.domain.Member;
import com.devoceanyoung.greendev.domain.member.dto.MemberReqDto;
import com.devoceanyoung.greendev.domain.member.dto.MemberResDto;
import com.devoceanyoung.greendev.domain.member.service.MemberService;
import com.devoceanyoung.greendev.global.constant.StatusEnum;
import com.devoceanyoung.greendev.global.dto.Message;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Message> readMember(@AuthUser Member member) {
		MemberResDto response = MemberResDto.of(member);
		return ResponseEntity.ok(Message.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(StatusEnum.OK.getCode())
			.data(response)
			.build());
	}


	@PatchMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Message> updateNickname(@AuthUser Member member,
		@RequestBody @Valid final MemberReqDto reqDto) {
		memberService.update(member.getMemberId(), reqDto);
		return ResponseEntity.ok(Message.builder()
			.status(StatusEnum.OK.getStatusCode())
			.message(NICKNAME_CHANGE_SUCCESS)
			.build());
	}
}
