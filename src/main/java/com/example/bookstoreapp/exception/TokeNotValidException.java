package com.example.bookstoreapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Jwt Token is not valid")
public class TokeNotValidException extends RuntimeException {
    public TokeNotValidException(){
        super("Jwt Token is not valid");
    }
}
