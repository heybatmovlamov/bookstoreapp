package com.example.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RefreshResponseDto {

    private String token;

    public RefreshResponseDto(){

    }

}
