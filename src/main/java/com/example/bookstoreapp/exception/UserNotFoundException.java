package com.example.bookstoreapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "You should login")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("You should login");
    }
}
