package com.spring.test.excpetion;

public class CustomException extends Exception {

    private String message;

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause); // Include cause for chained exceptions
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
