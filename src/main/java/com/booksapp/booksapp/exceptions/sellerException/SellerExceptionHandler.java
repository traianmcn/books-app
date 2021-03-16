package com.booksapp.booksapp.exceptions.sellerException;

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
public class SellerExceptionHandler {

    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<ApiError> handleSellerNotFoundException(SellerNotFoundException e, HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String querryString = request.getQueryString();
        String path = requestURL.append('?').append(querryString).toString();
        ApiError apiError = new ApiError(400, e.getMessage(), path);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
