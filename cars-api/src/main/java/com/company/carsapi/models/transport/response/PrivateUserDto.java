package com.company.carsapi.models.transport.response;

import java.time.LocalDateTime;

public class PrivateUserDto extends UserDto {

    private LocalDateTime lastLogin;

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
