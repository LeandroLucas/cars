package com.company.carsapi.models.transport.response;

public class SessionDto {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SessionDto{" +
                "token='" + token + '\'' +
                '}';
    }
}
