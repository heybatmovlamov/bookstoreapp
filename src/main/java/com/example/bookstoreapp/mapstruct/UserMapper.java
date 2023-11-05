package com.example.bookstoreapp.mapstruct;


import com.example.bookstoreapp.dto.VerifyResponseDto;
import com.example.bookstoreapp.entity.User;

public abstract class UserMapper {
    public abstract VerifyResponseDto mapToVerifyDto(User user);
}
