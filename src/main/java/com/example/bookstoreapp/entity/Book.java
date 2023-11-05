package com.example.bookstoreapp.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity(name = "book")
public class Book {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;










    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Author author;

    // Constructors, getters, and setters
}