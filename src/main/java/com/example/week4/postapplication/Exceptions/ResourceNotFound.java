package com.example.week4.postapplication.Exceptions;

public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound() {
        super();
    }

    public ResourceNotFound(String message) {
        super(message);
    }
}
