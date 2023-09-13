package com.company.carsapi.models.transport.response;

import com.company.carsapi.models.persistence.Car;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    private Long id;

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

    /**
     * Login do usuário
     */
    private String login;

    /**
     * Telefone do usuário
     */
    private String phone;

    /**
     * Data de nascimento do usuário
     */
    private LocalDate birthday;

    /**
     * Momento da criação do usuário
     */
    private LocalDateTime createdAt;

    /**
     * Lista de carros do usuário
     */
    private List<CarDto> cars;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<CarDto> getCars() {
        return cars;
    }

    public void setCars(List<CarDto> cars) {
        this.cars = cars;
    }
}
