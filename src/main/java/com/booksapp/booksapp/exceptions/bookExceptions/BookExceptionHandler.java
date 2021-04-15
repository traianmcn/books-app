package com.booksapp.booksapp.exceptions.bookExceptions;

import com.booksapp.booksapp.exceptions.ApiError;
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

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleBookNotFoundException(BookNotFoundException e, HttpServletRequest request) {
        StringBuilder requestUrl = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString(); // ==> result null
        String path = requestUrl.append('?').append(queryString).toString();
        ApiError apiError = new ApiError(400, e.getMessage(), path);
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }
}
