package com.example.week4.postapplication.Advice;

import com.example.week4.postapplication.Exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<APIError> resourceNotFound(ResourceNotFound rnf) {

        String currDT = giveCurrentDateTime();

        APIError apiError = APIError.builder()
                .message(rnf.getLocalizedMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorRecordedTime(currDT)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

    }

    //handles every other exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> internalServerError(Exception exception) {

        String currDT = giveCurrentDateTime();

        APIError apiError = APIError.builder()
                .message(exception.getMessage())
                .errorRecordedTime(currDT)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //returns current date and time when called
    private String giveCurrentDateTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
    }
}