package com.example.bookstoreapp.dto;

import com.example.bookstoreapp.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class VerifyResponseDto {

    public VerifyResponseDto() {
    }

    public VerifyResponseDto(String username) {
        this.name = username;
    }

    private Long id;
    private String name;
    private int age;
    private String password;
    private RoleEnum role;

}
