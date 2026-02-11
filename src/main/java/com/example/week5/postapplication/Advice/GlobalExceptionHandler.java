package com.example.week5.postapplication.Advice;

import com.example.week5.postapplication.Exceptions.ResourceNotFoundException;
import com.example.week5.postapplication.Exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> resourceNotFound(ResourceNotFoundException rnf) {

        String currDT = giveCurrentDateTime();

        APIError apiError = APIError.builder()
                .message(rnf.getLocalizedMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorRecordedTime(currDT)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<APIError> userAlreadyExistsExceptionExceptionHandler(UserAlreadyExistsException exception) {

        String currDT = giveCurrentDateTime();

        APIError apiError = APIError.builder()
                .message(exception.getMessage())
                .errorRecordedTime(currDT)
                .httpStatus(HttpStatus.CONFLICT)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIError> authenticationExceptionHandler(AuthenticationException exception) {

        String info = getExceptionNameAndMessage(exception);
        String currDT = giveCurrentDateTime();

        APIError apiError = APIError.builder()
                .message(info)
                .errorRecordedTime(currDT)
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    //handles every other exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> otherExceptionHandler(Exception exception) {

        String info = getExceptionNameAndMessage(exception);

        APIError apiError = APIError.builder()
                .message(info)
                .errorRecordedTime( giveCurrentDateTime() )
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //returns current date and time when called
    private String giveCurrentDateTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
    }

    //returns an exception's class name and message
    private String getExceptionNameAndMessage(Exception exception){
        StringBuilder exceptionInfo = new StringBuilder(exception.getClass().getName()+" , "+ exception.getMessage());
        return  exceptionInfo.toString();

    }
}