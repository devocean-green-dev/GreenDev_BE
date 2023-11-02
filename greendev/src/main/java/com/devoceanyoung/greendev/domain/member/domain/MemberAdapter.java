package com.devoceanyoung.greendev.domain.member.domain;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class MemberAdapter extends User { //본 Member에 바로 User을 상속 받으면 도메인 객체는 특정 기능에 종속되므로 Best prac 이 아님
	private Member member;

	public MemberAdapter(Member member) {// TODO:권한 추가시 수정
		super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
		this.member = member;
	}
}
