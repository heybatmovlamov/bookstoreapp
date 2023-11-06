package com.example.bookstoreapp.entity;

import com.example.bookstoreapp.enums.RoleEnum;
import com.example.bookstoreapp.enums.TokenClaims;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long id;
    String name;
    int age;
    String email;
    String password;
    @Enumerated(EnumType.STRING)
    RoleEnum role;
    @ManyToOne
    private Book book;

    @Transactional
    public Map<String, Object> getClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TokenClaims.USER_ID.getValue(), this.id);
        claims.put(TokenClaims.EMAIL.getValue(), this.email);
        claims.put(TokenClaims.ROLES.getValue(), this.role);
        return claims;
    }
}
