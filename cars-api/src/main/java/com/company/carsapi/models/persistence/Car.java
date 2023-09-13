package com.company.carsapi.models.persistence;

import com.company.carsapi.constants.DatabaseConstants;
import jakarta.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ano de fabricação do carro
     */
    @Column(nullable = false)
    private int year;

    /**
     * Placa do carro
     */
    @Column(nullable = false, length = DatabaseConstants.MAX_PLATE_LENGTH)
    private String licensePlate;

    /**
     * Modelo do carro
     */
    @Column(nullable = false, length = DatabaseConstants.MAX_MODEL_LENGTH)
    private String model;

    /**
     * Cor predominante do carro
     */
    @Column(nullable = false, length = DatabaseConstants.MAX_COLOR_LENGTH)
    private String color;

    /**
     * Contador de utilizações do carro
     */
    @Column(nullable = false)
    private int usageCounter = 0;

    /**
     * Usuário dono do carro
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

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

    public void setUser(User user) {
        this.user = user;
    }
}
