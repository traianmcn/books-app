package com.booksapp.booksapp.exceptions.bookExceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String msg) {
        super(msg);
    }
}
