package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.Book;
import com.example.bookstoreapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book>findBooksByCurrentlyReadingTrue();
}
