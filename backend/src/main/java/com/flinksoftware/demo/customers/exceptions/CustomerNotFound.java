package com.flinksoftware.demo.customers.exceptions;

public class CustomerNotFound extends RuntimeException {
    private static final long serialVersionUID = 1;

    public CustomerNotFound(String message) {
        super(message);
    }
}
