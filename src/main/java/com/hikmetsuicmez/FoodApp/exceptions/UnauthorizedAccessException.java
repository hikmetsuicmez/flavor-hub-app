package com.hikmetsuicmez.FoodApp.exceptions;

public class UnauthorizedAccessException extends RuntimeException{

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
