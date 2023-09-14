package com.company.carsapi.models.transport.response;

public class CarDto {

    private Long id;

    /**
     * Ano de fabricação do carro
     */
    private int year;

    /**
     * Placa do carro
     */
    private String licensePlate;

    /**
     * Modelo do carro
     */
    private String model;

    /**
     * Cor predominante do carro
     */
    private String color;

    /**
     * Contador de utilizações do veículo
     */
    private int usageCounter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getUsageCounter() {
        return usageCounter;
    }

    public void setUsageCounter(int usageCounter) {
        this.usageCounter = usageCounter;
    }
}
