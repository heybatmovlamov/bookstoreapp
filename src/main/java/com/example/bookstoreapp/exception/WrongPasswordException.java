package com.example.bookstoreapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Password wrong statement")
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(){
        super("Password wrong statement");
    }
}
