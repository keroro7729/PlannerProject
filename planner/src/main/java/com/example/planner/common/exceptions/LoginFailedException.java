package com.example.planner.common.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginFailedException extends RuntimeException {
    private final String email;

    public LoginFailedException() {
        super("password not match");
        this.email = null;
    }

    public LoginFailedException(String email) {
        super("email not exist: "+email);
        this.email = email;
    }
}
