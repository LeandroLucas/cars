package com.company.carsapi.repositories;

import com.company.carsapi.models.persistence.Car;
import org.springframework.data.repository.CrudRepository;
public interface CarRepository extends CrudRepository<Car, Long> {
}
