package com.company.carsapi.services;

import com.company.carsapi.models.persistence.Car;
import com.company.carsapi.models.persistence.User;
import com.company.carsapi.models.transport.creation.EditCar;
import com.company.carsapi.models.transport.response.CarDto;
import com.company.carsapi.repositories.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Classe responsável por manipular a tabela 'Car'
 */
@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional
    public void deleteAll(List<Car> cars) {
        this.carRepository.deleteAll(cars);
    }

    public Car preparePersistenceCar(User user, EditCar carData) {
        Car car = new Car();
        car.setColor(carData.getColor());
        car.setModel(carData.getModel());
        car.setLicensePlate(carData.getLicensePlate());
        car.setYear(carData.getYear());
        car.setUser(user);
        return car;
    }

    /**
     * Converte objeto de persistencia do carro para objeto de transporte
     * @param car Objeto que será convertido
     * @return CarDto com as informações do carro
     */
    public CarDto persistencetoDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setYear(car.getYear());
        carDto.setColor(car.getColor());
        carDto.setModel(car.getModel());
        carDto.setLicensePlate(car.getLicensePlate());
        return carDto;
    }
}
