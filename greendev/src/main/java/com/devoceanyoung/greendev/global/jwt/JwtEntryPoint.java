package com.devoceanyoung.greendev.global.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import net.minidev.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint { //인증에 실패할 경우 진행될 EntryPoint를 생성

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws
		IOException,
		ServletException, IOException {

		log.error("Unauthorized Error : {}", authException.getMessage());
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(new JSONObject()
			.put("message", authException.getMessage()).toString()); //TODO: exception 글로벌하게 만들기
	}


}
