package com.example.AcmeBank.Error;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value={CustomException.class})
    public ResponseEntity<Object> handleApiRequestException(CustomException customException){
       ExceptionBody exceptionBody=  new ExceptionBody(
                customException.getMessage(),
                HttpStatus.BAD_REQUEST
        );
       return new ResponseEntity<>(exceptionBody,exceptionBody.getHttpStatus());
    }
}
