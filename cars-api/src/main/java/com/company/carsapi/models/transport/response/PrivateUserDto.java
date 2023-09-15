package com.company.carsapi.models.transport.response;

import java.time.LocalDateTime;

public class PrivateUserDto extends GetUserDto {

    /**
     * Data do último login do usuário
     */
    private LocalDateTime lastLogin;

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


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
