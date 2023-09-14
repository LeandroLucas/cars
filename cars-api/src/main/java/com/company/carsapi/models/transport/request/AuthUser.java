package com.company.carsapi.models.transport.request;

import com.company.carsapi.constants.DatabaseConstants;
import com.company.carsapi.constants.ValidationConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuthUser {

    @Size(max = DatabaseConstants.MAX_LOGIN_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String login;

    @Size(max = DatabaseConstants.MAX_PASSWORD_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
