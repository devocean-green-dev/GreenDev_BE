package com.devoceanyoung.greendev.domain.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppLoginReqDto {
    @NotBlank(message = "이메일은 필수입니다.")//해당 값이 null이 아니고, 공백(""과 " " 모두 포함)이 아닌지 검증
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotBlank(message = "닉네임은 필수입니다. ")
    private String nickname;

    @NotBlank(message = "사진은 필수입니다.")
    private String profileImageUrl;

    @NotBlank(message = "username은 필수입니다.")
    private String username;

    public AppLoginReqDto(String email, String nickname, String profileImageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
