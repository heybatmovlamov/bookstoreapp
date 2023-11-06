package com.example.bookstoreapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Username already exist")
public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super("Username already exist");
    }
}
