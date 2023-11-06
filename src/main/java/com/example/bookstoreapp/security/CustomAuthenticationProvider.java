package com.example.bookstoreapp.security;

import com.example.bookstoreapp.enums.AuthenticationType;
import com.example.bookstoreapp.security.authentication.AuthenticationStrategy;
import com.example.bookstoreapp.security.authentication.AuthorAuthenticationStrategy;
import com.example.bookstoreapp.security.authentication.StudentAuthenticationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider {
    private final AuthorAuthenticationStrategy authorAuthenticationStrategy;
    private final StudentAuthenticationStrategy studentAuthenticationStrategy;
    public AuthenticationStrategy create(AuthenticationType type){
        return switch (type){
            case AUTHOR -> authorAuthenticationStrategy;
            case STUDENT -> studentAuthenticationStrategy;
        };
    }
}
