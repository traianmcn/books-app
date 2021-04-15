package com.booksapp.booksapp.exceptions.orderExceptions;

public class CancelOrderException extends RuntimeException {
    public CancelOrderException(String msg) {
        super(msg);
    }
}
