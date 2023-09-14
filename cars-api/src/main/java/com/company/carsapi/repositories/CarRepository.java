package com.company.carsapi.repositories;

import com.company.carsapi.models.persistence.Car;
import com.company.carsapi.models.persistence.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {

    List<Car> findByUser(User user);

    Optional<Car> findByUserAndId(User user, Long id);

}
