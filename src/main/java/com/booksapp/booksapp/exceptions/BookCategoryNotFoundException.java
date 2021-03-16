package com.booksapp.booksapp.exceptions;

public class BookCategoryNotFoundException extends RuntimeException {
    public BookCategoryNotFoundException(String msg) {
        super(msg);
    }
}
