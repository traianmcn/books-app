package com.booksapp.booksapp.exceptions.orderExceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String msg) {
        super(msg);
    }
}
