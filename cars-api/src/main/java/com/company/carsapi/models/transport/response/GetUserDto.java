package com.company.carsapi.models.transport.response;

import java.time.LocalDate;
import java.util.List;

public class GetUserDto extends ListUserDto {

    /**
     * Telefone do usuário
     */
    private String phone;

    /**
     * Data de nascimento do usuário
     */
    private LocalDate birthday;

    private List<CarDto> cars;

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

    public List<CarDto> getCars() {
        return cars;
    }

    public void setCars(List<CarDto> cars) {
        this.cars = cars;
    }
}
