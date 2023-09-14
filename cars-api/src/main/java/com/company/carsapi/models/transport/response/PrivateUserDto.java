package com.company.carsapi.models.transport.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PrivateUserDto extends UserDto {

    private LocalDateTime lastLogin;


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

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
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
}
