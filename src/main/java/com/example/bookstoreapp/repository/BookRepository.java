package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//    List<Book> findByAuthor(Author author);
}
