package com.example.bookstoreapp.dto;

import com.example.bookstoreapp.enums.AuthenticationType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginRequestDto {

    public LoginRequestDto(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }
    public LoginRequestDto(){

    }


    @NotEmpty(message = "username cant be empty")
    private String username;



    @NotEmpty(message = "password cant be empty")
    private String password;

    private AuthenticationType type;

}