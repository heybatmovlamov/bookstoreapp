package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author getAuthorById(Long id);
}
