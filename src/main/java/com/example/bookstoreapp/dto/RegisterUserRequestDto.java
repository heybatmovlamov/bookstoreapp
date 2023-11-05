package com.example.bookstoreapp.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUserRequestDto {
    String name;
    String password;
    String confirmedPassword;
}
