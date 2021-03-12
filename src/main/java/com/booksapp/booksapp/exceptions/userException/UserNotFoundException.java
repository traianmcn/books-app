package com.booksapp.booksapp.exceptions.userException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (String msg) {
        super(msg);
    }
}
