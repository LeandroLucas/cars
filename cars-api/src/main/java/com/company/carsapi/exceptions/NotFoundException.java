package com.company.carsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends AbstractException {

    /**
     * @param entityName Informe o nome da entidade para a mensagem padrão da exception
     */
    public NotFoundException(String entityName) {
        super(entityName + " not found");
    }

    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
