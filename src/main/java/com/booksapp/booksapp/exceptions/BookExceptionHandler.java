package com.booksapp.booksapp.exceptions;

import com.booksapp.booksapp.exceptions.customerException.CustomerNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BookExceptionHandler {

    @ExceptionHandler(BookCategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleBookCategoryNotFound(BookCategoryNotFoundException e, HttpServletRequest request) {
        StringBuilder requestUrl = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString(); // ==> result null
        String path = requestUrl.append('?').append(queryString).toString();
        ApiError apiError = new ApiError(400, e.getMessage(), path);
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }
}
