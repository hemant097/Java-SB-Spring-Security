package com.example.week5.postapplication.Exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(){super();}

    public UserAlreadyExistsException(String email) {
        super("User with email '" + email + "' already exists");
    }
}
