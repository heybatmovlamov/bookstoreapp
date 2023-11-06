package com.example.bookstoreapp.dto;

import com.example.bookstoreapp.entity.Author;
import com.example.bookstoreapp.entity.Student;
import com.example.bookstoreapp.entity.User;
import com.example.bookstoreapp.enums.RoleEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReaderResponseDto {

    Set<ReaderDto>readers;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ReaderDto{
        Long readerId;
        String bookName;
        RoleEnum role;
    }
}
