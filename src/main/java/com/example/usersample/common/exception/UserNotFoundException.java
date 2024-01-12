package com.example.usersample.common.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Object value) {
        super("User not found: " + value);
    }
}
