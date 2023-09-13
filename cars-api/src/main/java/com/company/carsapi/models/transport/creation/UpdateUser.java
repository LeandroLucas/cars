package com.company.carsapi.models.transport.creation;

import com.company.carsapi.constants.DatabaseConstants;
import com.company.carsapi.constants.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe responsável pela validação dos campos de criação e atualização de usuário
 */
public class UpdateUser {

    @Size(max = DatabaseConstants.MAX_NAME_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String firstName;

    /**
     * Sobrenome do usuário
     */
    @Size(max = DatabaseConstants.MAX_NAME_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String lastName;

    /**
     * E-mail do usuário
     */
    @Size(max = DatabaseConstants.MAX_EMAIL_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @Email(message =  ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String email;

    /**
     * Login do usuário
     */
    @Size(max = DatabaseConstants.MAX_LOGIN_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String login;

    /**
     * Senha do usuário
     */
    @Size(max = DatabaseConstants.MAX_PASSWORD_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String password;

    /**
     * Telefone do usuário
     */
    @Size(max = DatabaseConstants.MAX_PHONE_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String phone;

    /**
     * Data de nascimento do usuário
     */
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private LocalDate birthday;

    /**
     * Lista de carros do usuário
     */
    private List<EditCar> cars;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<EditCar> getCars() {
        return cars;
    }

    public void setCars(List<EditCar> cars) {
        this.cars = cars;
    }
}
