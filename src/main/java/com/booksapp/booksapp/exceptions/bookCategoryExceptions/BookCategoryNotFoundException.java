package com.booksapp.booksapp.exceptions.bookCategoryExceptions;

public class BookCategoryNotFoundException extends RuntimeException {
    public BookCategoryNotFoundException(String msg) {
        super(msg);
    }
}
