package com.booksapp.booksapp.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (String msg) {
        super(msg);
    }
}
