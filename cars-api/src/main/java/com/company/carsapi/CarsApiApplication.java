package com.company.carsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarsApiApplication.class, args);
    }

}
