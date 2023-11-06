package com.example.bookstoreapp.service;

import com.example.bookstoreapp.entity.Book;
import com.example.bookstoreapp.entity.Student;
import com.example.bookstoreapp.repository.BookRepository;
import com.example.bookstoreapp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final BookRepository bookRepository;
    public List<Book> getListOfBooksThatCurrentlyReading() {
        return bookRepository.findBooksByCurrentlyReadingTrue();
    }
}
