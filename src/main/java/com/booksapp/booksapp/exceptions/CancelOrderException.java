package com.booksapp.booksapp.exceptions;

public class CancelOrderException extends RuntimeException {
    public CancelOrderException(String msg) {
        super(msg);
    }
}
