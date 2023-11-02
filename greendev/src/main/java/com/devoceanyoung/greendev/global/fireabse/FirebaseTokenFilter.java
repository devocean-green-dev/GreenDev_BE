package com.devoceanyoung.greendev.global.fireabse;

import com.devoceanyoung.greendev.domain.auth.exception.TokenVerificationException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FirebaseTokenFilter {
    public FirebaseToken verifyToken(String idToken) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            // 여기에서 더 구체적인 예외 처리를 할 수 있습니다.
            // 예를 들어, 사용자 정의 예외를 던질 수 있습니다.
            throw new TokenVerificationException("firebase Token is not valid", e);
        }
    }

}
