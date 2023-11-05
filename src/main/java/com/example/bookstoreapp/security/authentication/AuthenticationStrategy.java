package com.example.bookstoreapp.security.authentication;

import com.example.bookstoreapp.dto.AuthenticationResponseDto;
import com.example.bookstoreapp.dto.GenericResponse;
import com.example.bookstoreapp.dto.VerifyResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationStrategy {
    GenericResponse<Void> register(Object request);

    GenericResponse<AuthenticationResponseDto> login(Object requestDto);

    GenericResponse<VerifyResponseDto> verifyToken(HttpServletRequest request);

    GenericResponse<? extends Object> refreshToken(HttpServletRequest request);
}
