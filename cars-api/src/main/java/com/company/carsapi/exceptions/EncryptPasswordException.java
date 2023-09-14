/**
 * Mar 11, 2020
 */
package com.company.carsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EncryptPasswordException extends AbstractException {

    public EncryptPasswordException() {
        super();
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    public EncryptPasswordException(String message) {
        super(message);
    }
}
