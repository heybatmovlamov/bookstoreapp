package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.AuthenticationResponseDto;
import com.example.bookstoreapp.dto.GenericResponse;
import com.example.bookstoreapp.dto.LoginRequestDto;
import com.example.bookstoreapp.dto.RegisterUserRequestDto;
import com.example.bookstoreapp.entity.Author;
import com.example.bookstoreapp.enums.AuthenticationType;
import com.example.bookstoreapp.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {
    public final AuthorService authorService;
    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping("/save")
    public Author createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }


}

