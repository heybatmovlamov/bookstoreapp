package com.example.bookstoreapp.entity;


import com.example.bookstoreapp.enums.RoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("A")
public class Author extends User{
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private Set<Student>students;
    @OneToMany(mappedBy = "author")
    Set<Book>books;
}

