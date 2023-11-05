package com.example.bookstoreapp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponseDto {
    private String authenticationToken;
}
