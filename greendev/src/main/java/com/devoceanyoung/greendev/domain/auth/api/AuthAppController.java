package com.devoceanyoung.greendev.domain.auth.api;

import com.devoceanyoung.greendev.domain.auth.dto.AccessTokenDto;
import com.devoceanyoung.greendev.domain.auth.dto.AppLoginReqDto;
import com.devoceanyoung.greendev.domain.auth.dto.FirebaseAuthToken;
import com.devoceanyoung.greendev.domain.auth.dto.RefreshAccessTokenResDto;
import com.devoceanyoung.greendev.domain.auth.dto.RefreshTokenResDto;
import com.devoceanyoung.greendev.domain.auth.dto.TokenResDto;
import com.devoceanyoung.greendev.domain.auth.service.AuthService;
import com.devoceanyoung.greendev.global.constant.StatusEnum;
import com.devoceanyoung.greendev.global.dto.StatusResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("api/v1/app/token")
@RequiredArgsConstructor
public class AuthAppController {
    private final AuthService authService;

    @PostMapping("/login/firebase")
    public ResponseEntity<StatusResponse> firebaseLogin(@RequestBody FirebaseAuthToken firebaseAuthToken){
       TokenResDto resDto = authService.firebaseLogin(firebaseAuthToken);
        return ResponseEntity.ok(StatusResponse.builder()
                .status(StatusEnum.OK.getStatusCode())
                .message(StatusEnum.OK.getCode())
                .data(resDto)
                .build());
    }

    @PostMapping("/login/{providerType}")
    public ResponseEntity<StatusResponse> login(@PathVariable String providerType, @Valid @RequestBody AppLoginReqDto appLoginReqDto){
        TokenResDto resDto = authService.login(providerType, appLoginReqDto);
        return ResponseEntity.ok(StatusResponse.builder()
                .status(StatusEnum.OK.getStatusCode())
                .message(StatusEnum.OK.getCode())
                .data(resDto)
                .build());
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<StatusResponse> getRefreshToken(@RequestBody AccessTokenDto accessTokenDto){
        RefreshTokenResDto resDto = authService.getRefreshToken(accessTokenDto);
        return ResponseEntity.ok(StatusResponse.builder()
                .status(StatusEnum.OK.getStatusCode())
                .message(StatusEnum.OK.getCode())
                .data(resDto)
                .build());

    }

    @PostMapping("/refresh")
    public ResponseEntity<StatusResponse> refresh(@RequestBody RefreshAccessTokenResDto reqDto) {
        AccessTokenDto resDto = authService.refresh(reqDto.getRefreshToken());

        return ResponseEntity.ok(StatusResponse.builder()
                .status(StatusEnum.OK.getStatusCode())
                .message(StatusEnum.OK.getCode())
                .data(resDto)
                .build());
    }

    @PostMapping("/blacklist")
    public ResponseEntity<StatusResponse> signOut(@RequestBody AccessTokenDto requestDto) {
        AccessTokenDto resDto = authService.signOut(requestDto);

        return ResponseEntity.ok()
                .body(StatusResponse.builder()
                        .status(StatusEnum.OK.getStatusCode())
                        .message(StatusEnum.OK.getCode())
                        .data(resDto)
                        .build());

    }
}
