package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.Author;
import com.example.bookstoreapp.entity.Student;
import com.example.bookstoreapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}