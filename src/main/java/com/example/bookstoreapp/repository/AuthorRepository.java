package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author save(Author author);
    Author getAuthorById(Long id);
    // You can define additional methods here if needed
}
