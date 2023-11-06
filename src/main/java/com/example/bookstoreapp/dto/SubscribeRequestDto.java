package com.example.bookstoreapp.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscribeRequestDto {
    Long studentId;
    Long authorId;
}
