package com.company.carsapi.exceptions;

public abstract class AbstractException extends RuntimeException {

    public AbstractException(String msg) {
        super(msg);
    }

    public AbstractException() {
        super();
    }

    public abstract int getStatusCode();
}
