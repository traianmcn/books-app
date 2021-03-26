package com.booksapp.booksapp.exceptions.userException;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException (String msg) {
        super(msg);
    }
}
