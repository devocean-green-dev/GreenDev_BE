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
            throw new TokenVerificationException("firebase Token is not valid", e);
        }
    }

}
