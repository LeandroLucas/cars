package com.company.carsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthorizationException extends AbstractException {

    public AuthorizationException() {
        super("Unauthorized");
    }

    public AuthorizationException(String msg) {
        super(msg);
    }

    public int getStatusCode() {
        return HttpStatus.FORBIDDEN.value();
    }
}
