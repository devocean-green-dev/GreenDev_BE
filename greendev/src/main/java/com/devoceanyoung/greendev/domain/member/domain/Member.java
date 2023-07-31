package com.devoceanyoung.greendev.domain.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.devoceanyoung.greendev.global.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity//해당 클래스에 있는 내부변수에 모두 @Column을 내부적으로 포함 -> 옵셥없으면 생략 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어를 PROTECTED로 설정해놓게 되면 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단
@Getter
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id", updatable = false)
	private Long memberId;

	private String username;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Column(length = 50)
	private String nickname;

	private String password;

	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private ProviderType providerType;

	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Builder
	public Member(String email, String profileImageUrl, String nickname, String password,
		ProviderType providerType, RoleType roleType, String username) {
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.nickname = nickname;
		this.password = password;
		this.providerType = providerType;
		this.roleType = RoleType.USER;
		this.username = username;

	}

	public void updateMember(String email){
		this.email = email;
	}

	public void updateNickname(String nickname){
		this.nickname = nickname;
	}
	public void updateRole(RoleType roleType){this.roleType = roleType;}
}
