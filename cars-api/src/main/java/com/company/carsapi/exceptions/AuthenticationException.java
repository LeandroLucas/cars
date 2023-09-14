package com.company.carsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends AbstractException {

    public AuthenticationException() {
        super("Invalid login or password");
    }

    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
