package com.example.bookstoreapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Password must be same")
public class PasswordRepeatNotSameException extends RuntimeException {
    public PasswordRepeatNotSameException() {
        super("Password must be same");
    }
}
