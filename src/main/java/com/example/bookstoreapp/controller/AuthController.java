package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.*;
import com.example.bookstoreapp.enums.AuthenticationType;
import com.example.bookstoreapp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    public final AuthenticationService authenticationService;
    @PostMapping(value = "/register")
    public GenericResponse<Void> register(@RequestBody RegisterUserRequestDto requestDto){
        return authenticationService.register(requestDto,requestDto.getType());
    }

    @PostMapping(value = "/login")
    public GenericResponse<AuthenticationResponseDto>login(@RequestBody LoginRequestDto requestDto){
        return authenticationService.login(requestDto,requestDto.getType());
    }

    @GetMapping("/verify")
    public GenericResponse<VerifyResponseDto> verifyToken(HttpServletRequest request) {
        return authenticationService.verifyToken(request, AuthenticationType.AUTHOR);
    }
    @GetMapping("/refresh")
    public GenericResponse<RefreshResponseDto> refreshTokens(HttpServletRequest request) {
        return authenticationService.refreshToken(request, AuthenticationType.AUTHOR);
    }

}
