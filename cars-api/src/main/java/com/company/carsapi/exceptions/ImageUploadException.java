package com.company.carsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ImageUploadException extends AbstractException {

    public ImageUploadException() {
        super("Image Upload Error");
    }

    public ImageUploadException(String msg) {
        super(msg);
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
