package com.company.carsapi.models.transport.response;

import com.company.carsapi.models.persistence.Car;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    private Long id;

    /**
     * Login do usuário
     */
    private String login;

    /**
     * Nome do usuário
     */
    private String firstName;

    /**
     * Sobrenome do usuário
     */
    private String lastName;

    /**
     * E-mail do usuário
     */
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
