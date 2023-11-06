package com.example.bookstoreapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Author author;
    @ManyToOne
    private Student student;
    @OneToMany(mappedBy = "book")
    private Set<User>users;
    private boolean currentlyReading;
}