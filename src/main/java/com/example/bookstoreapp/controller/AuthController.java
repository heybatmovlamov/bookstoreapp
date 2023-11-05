package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.*;
import com.example.bookstoreapp.enums.AuthenticationType;
import com.example.bookstoreapp.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    public final AuthorService authorService;
    @PostMapping(value = "/register")
    public GenericResponse<Void> register(@RequestBody RegisterUserRequestDto requestDto){
        return authorService.register(requestDto, AuthenticationType.AUTHOR);
    }

    @PostMapping(value = "/login")
    public GenericResponse<AuthenticationResponseDto>login(@RequestBody LoginRequestDto requestDto){
        return authorService.login(requestDto,AuthenticationType.AUTHOR);
    }

    @GetMapping("/verify")
    public GenericResponse<VerifyResponseDto> verifyToken(HttpServletRequest request) {
        return authorService.verifyToken(request, AuthenticationType.AUTHOR);
    }
    @GetMapping("/refresh")
    public GenericResponse<RefreshResponseDto> refreshTokens(HttpServletRequest request) {
        return authorService.refreshToken(request, AuthenticationType.AUTHOR);
    }

}
