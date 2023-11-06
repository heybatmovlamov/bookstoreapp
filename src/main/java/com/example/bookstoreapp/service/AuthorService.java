package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.*;
import com.example.bookstoreapp.entity.Author;
import com.example.bookstoreapp.entity.Book;
import com.example.bookstoreapp.enums.AuthenticationType;
import com.example.bookstoreapp.repository.AuthorRepository;
import com.example.bookstoreapp.repository.BookRepository;
import com.example.bookstoreapp.security.CustomAuthenticationProvider;
import com.example.bookstoreapp.security.authentication.AuthenticationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final BookRepository bookRepository;



    public void createBook(BookRequestDto requestDto) {
        Book book=new Book();
        book.setName(requestDto.getName());
        Author author = authorRepository.getAuthorById(requestDto.getAuthorId());
        book.setAuthor(author);
        author.setBooks(Collections.singleton(book));
        authorRepository.save(author);
    }

    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).get();
        bookRepository.delete(book);
    }
}
