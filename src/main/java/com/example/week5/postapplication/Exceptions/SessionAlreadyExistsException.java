package com.example.week5.postapplication.Exceptions;

public class SessionAlreadyExistsException extends RuntimeException {

    public SessionAlreadyExistsException(){super();}

    public SessionAlreadyExistsException(String email) {
        super("Session with user '" + email + "' already exists, try again after some time");
    }
}