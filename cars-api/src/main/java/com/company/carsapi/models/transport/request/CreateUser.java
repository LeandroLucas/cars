package com.company.carsapi.models.transport.request;

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
public class CreateUser extends EditUser {

    /**
     * Lista de carros do usuário
     */
    private List<EditCar> cars;

    public List<EditCar> getCars() {
        return cars;
    }

    public void setCars(List<EditCar> cars) {
        this.cars = cars;
    }
}
