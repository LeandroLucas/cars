package com.company.carsapi.models.persistence;

import com.company.carsapi.constants.DatabaseConstants;
import jakarta.persistence.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do usuário
     */
    @Column(nullable = false, length = DatabaseConstants.MAX_NAME_LENGTH)
    private String firstName;

    /**
     * Sobrenome do usuário
     */
    @Column(nullable = false, length = DatabaseConstants.MAX_NAME_LENGTH)
    private String lastName;

    /**
     * E-mail do usuário
     */
    @Column(nullable = false, unique = true, length = DatabaseConstants.MAX_EMAIL_LENGTH)
    private String email;

    /**
     * Login do usuário
     */
    @Column(nullable = false, unique = true, length = DatabaseConstants.MAX_LOGIN_LENGTH)
    private String login;

    /**
     * Senha do usuário
     */
    @Column(nullable = false, length = DatabaseConstants.MAX_PASSWORD_LENGTH)
    private String password;

    /**
     * Telefone do usuário
     */
    @Column(nullable = false, length = DatabaseConstants.MAX_PHONE_LENGTH)
    private String phone;

    /**
     * Data de nascimento do usuário
     */
    @Column(nullable = false)
    private LocalDate birthday;

    /**
     * Momento da criação do usuário
     */
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Lista de carros do usuário
     */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user")
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<Car> cars;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Car> getCars() {
        if(this.cars == null) {
            this.cars = new ArrayList<Car>();
        }
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
