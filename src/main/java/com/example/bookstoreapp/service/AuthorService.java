package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.*;
import com.example.bookstoreapp.entity.Author;
import com.example.bookstoreapp.enums.AuthenticationType;
import com.example.bookstoreapp.repository.AuthorRepository;
import com.example.bookstoreapp.security.CustomAuthenticationProvider;
import com.example.bookstoreapp.security.authentication.AuthenticationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }
    public Author createAuthor(Author author){
        return authorRepository.save(author);

    }
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
