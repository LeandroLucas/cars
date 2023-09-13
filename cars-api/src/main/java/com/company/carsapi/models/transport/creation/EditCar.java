package com.company.carsapi.models.transport.creation;

import com.company.carsapi.constants.DatabaseConstants;
import com.company.carsapi.constants.ValidationConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Classe responsável pela validação dos campos de criação e atualização de carros
 */
public class EditCar {

    /**
     * Ano de fabricação do carro
     */
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private int year;

    /**
     * Placa do carro
     */
    @Size(max = DatabaseConstants.MAX_PLATE_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String licensePlate;

    /**
     * Modelo do carro
     */
    @Size(max = DatabaseConstants.MAX_MODEL_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String model;

    /**
     * Cor predominante do carro
     */
    @Size(max = DatabaseConstants.MAX_COLOR_LENGTH, message = ValidationConstants.INVALID_FIELDS_MESSAGE)
    @NotNull(message = ValidationConstants.MISSING_FIELDS_MESSAGE)
    private String color;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
