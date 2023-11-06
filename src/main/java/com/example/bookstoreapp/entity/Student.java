package com.example.bookstoreapp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("S")
public class Student extends User{
    @ManyToOne
    @JoinColumn(name = "subscriber_id",referencedColumnName = "id")
    private Author author;
    @OneToMany(mappedBy = "student")
    Set<Book>books;
}

