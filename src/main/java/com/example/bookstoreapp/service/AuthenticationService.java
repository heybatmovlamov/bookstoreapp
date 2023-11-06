package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.*;
import com.example.bookstoreapp.enums.AuthenticationType;
import com.example.bookstoreapp.security.CustomAuthenticationProvider;
import com.example.bookstoreapp.security.authentication.AuthenticationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomAuthenticationProvider customAuthenticationProvider;
    public GenericResponse<Void> register(Object requestDto, AuthenticationType type) {
        AuthenticationStrategy authenticationStrategy = customAuthenticationProvider.create(type);
        authenticationStrategy.register(requestDto);
        return GenericResponse.success("SUCCESS REGISTERED");
    }

    public GenericResponse<AuthenticationResponseDto> login(LoginRequestDto requestDto, AuthenticationType authenticationType) {
        AuthenticationStrategy authenticationStrategy = customAuthenticationProvider.create(authenticationType);
        return GenericResponse.success(authenticationStrategy.login(requestDto).getData(),"SUCCESS");
    }

    public GenericResponse<VerifyResponseDto> verifyToken(HttpServletRequest request, AuthenticationType authenticationType) {
        AuthenticationStrategy authenticationStrategy = customAuthenticationProvider.create(authenticationType);
        return authenticationStrategy.verifyToken(request);
    }
    public GenericResponse<RefreshResponseDto> refreshToken(HttpServletRequest request, AuthenticationType authenticationType) {
        AuthenticationStrategy authenticationStrategy = customAuthenticationProvider.create(authenticationType);
        return GenericResponse.success(authenticationStrategy.refreshToken(request));
    }
}
