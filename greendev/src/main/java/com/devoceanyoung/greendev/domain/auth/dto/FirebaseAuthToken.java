package com.devoceanyoung.greendev.domain.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FirebaseAuthToken {
    @NotBlank(message = "Firebase Auth Token 은 필수로 입력되어야 합니다.")
    private String firebaseAuthToken;

    public FirebaseAuthToken(String firebaseAuthToken) {
        this.firebaseAuthToken = firebaseAuthToken;
    }
}
