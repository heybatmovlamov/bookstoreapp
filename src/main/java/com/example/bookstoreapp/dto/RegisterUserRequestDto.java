package com.example.bookstoreapp.dto;

import com.example.bookstoreapp.enums.AuthenticationType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUserRequestDto{
    String name;
    int age;
    String email;
    String password;
    String confirmedPassword;
    AuthenticationType type;
}
