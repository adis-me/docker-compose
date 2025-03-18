package com.flinksoftware.demo.security.exception;

import jakarta.security.auth.message.AuthException;

import java.io.Serial;

public final class UserNotActivatedException extends AuthException {
    @Serial
    private static final long serialVersionUID = -8599475170893598590L;

    public UserNotActivatedException(String message) {
        super(message);
    }
}
