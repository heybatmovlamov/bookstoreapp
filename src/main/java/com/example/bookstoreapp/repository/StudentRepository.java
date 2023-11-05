package com.example.bookstoreapp.repository;

import com.example.bookstoreapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // You can define additional methods here if needed
}
