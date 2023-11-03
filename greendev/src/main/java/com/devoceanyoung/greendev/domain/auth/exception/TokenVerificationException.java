package com.devoceanyoung.greendev.domain.auth.exception;

public class TokenVerificationException extends RuntimeException{
    public TokenVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

}
