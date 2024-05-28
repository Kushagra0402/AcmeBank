package com.example.AcmeBank.Error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionBody {

    private final String message;
    private final HttpStatus httpStatus;

    public ExceptionBody(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
