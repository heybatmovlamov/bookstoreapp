package com.example.bookstoreapp.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("A")
public class Author extends User{ }

