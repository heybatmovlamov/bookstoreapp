package com.example.bookstoreapp.security.authentication;

import com.example.bookstoreapp.dto.AuthenticationResponseDto;
import com.example.bookstoreapp.dto.GenericResponse;
import com.example.bookstoreapp.dto.RegisterUserRequestDto;
import com.example.bookstoreapp.dto.VerifyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class StudentAuthenticationStrategy implements AuthenticationStrategy{
    @Override
    public GenericResponse<Void> register(Object request) {
        RegisterUserRequestDto requestDto=(RegisterUserRequestDto) request;
        return null;
    }

    @Override
    public GenericResponse<AuthenticationResponseDto> login(Object requestDto) {
        return null;
    }

    @Override
    public GenericResponse<VerifyResponseDto> verifyToken(HttpServletRequest request) {
        return null;
    }

    @Override
    public GenericResponse<? extends Object> refreshToken(HttpServletRequest request) {
        return null;
    }
}
