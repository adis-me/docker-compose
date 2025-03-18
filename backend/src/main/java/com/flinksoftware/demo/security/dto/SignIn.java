package com.flinksoftware.demo.security.dto;

import lombok.Value;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Value
public class SignIn {
    private static final int MIN_PASSWORD_SIZE = 6;
    private static final int MAX_PASSWORD_SIZE = 255;
    private static final int MAX_USERNAME_SIZE = 100;
    @NotNull
    @Size(min = MIN_PASSWORD_SIZE, max = MAX_PASSWORD_SIZE)
    private String password;

    @NotNull
    @Email
    @Size(max = MAX_USERNAME_SIZE)
    private String username;
}
