package com.booksapp.booksapp.security;

public class JWTModel {

    private String token;

    public JWTModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
