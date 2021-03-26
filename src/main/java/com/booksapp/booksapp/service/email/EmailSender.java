package com.booksapp.booksapp.service.email;

public interface EmailSender {

    void sendEmail(String destination, String message);
}
