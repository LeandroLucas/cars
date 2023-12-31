package com.company.carsapi.services;

import com.company.carsapi.exceptions.NotFoundException;
import com.company.carsapi.models.persistence.Car;
import com.company.carsapi.models.persistence.Session;
import com.company.carsapi.models.persistence.User;
import com.company.carsapi.models.transport.request.EditCar;
import com.company.carsapi.models.transport.response.CarDto;
import com.company.carsapi.repositories.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por manipular a tabela 'Car'
 */
@Service
public class CarService {

    private final CarRepository carRepository;
    private final AuthService authService;

    public CarService(CarRepository carRepository, AuthService authService) {
        this.carRepository = carRepository;
        this.authService = authService;
    }

    @Transactional
    public CarDto createCar(String token, EditCar carToCreate) {
        Session session = this.authService.checkSession(token);
        Car car = this.preparePersistenceCar(session.getUser(), carToCreate);
        car = this.carRepository.save(car);
        return this.persistenceToDto(car);
    }

    public void updateBySessionAndId(String token, Long id, EditCar carToUpdate) {
        Session session = this.authService.checkSession(token);
        Car car = this.findByUserAndId(session.getUser(), id);
        this.applyToPersistence(car, carToUpdate);
        this.carRepository.save(car);
    }

    public Car preparePersistenceCar(User user, EditCar carData) {
        Car car = new Car();
        car.setUser(user);
        this.applyToPersistence(car, carData);
        return car;
    }

    private void applyToPersistence(Car car, EditCar carData) {
        car.setColor(carData.getColor());
        car.setModel(carData.getModel());
        car.setLicensePlate(carData.getLicensePlate());
        car.setYear(carData.getYear());
    }

    /**
     * Converte objeto de persistencia do carro para objeto de transporte
     *
     * @param car Objeto que será convertido
     * @return CarDto com as informações do carro
     */
    public CarDto persistenceToDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setYear(car.getYear());
        carDto.setColor(car.getColor());
        carDto.setModel(car.getModel());
        carDto.setLicensePlate(car.getLicensePlate());
        carDto.setUsageCounter(car.getUsageCounter());
        carDto.setImageName(car.getImageName());
        return carDto;
    }

    public List<CarDto> listBySession(String token) {
        Session session = this.authService.checkSession(token);
        List<Car> cars = this.carRepository.findByUser(session.getUser());
        return this.toDtoList(cars);
    }

    @Transactional
    public CarDto getBySessionAndId(String token, Long id) {
        Session session = this.authService.checkSession(token);
        Car car = this.findByUserAndId(session.getUser(), id);
        car.setUsageCounter(car.getUsageCounter() + 1);
        this.carRepository.save(car);
        return this.persistenceToDto(car);
    }

    @Transactional
    public void deleteBySessionAndId(String token, Long id) {
        Session session = this.authService.checkSession(token);
        Car car = this.findByUserAndId(session.getUser(), id);
        this.carRepository.delete(car);
    }

    public List<CarDto> toDtoList(List<Car> cars) {
        return cars.stream()
                .sorted((carA, carB) -> {
                    if (carA.getUsageCounter() < carB.getUsageCounter())
                        return 1;
                    else if (carA.getUsageCounter() == carB.getUsageCounter())
                        return carA.getModel().compareToIgnoreCase(carB.getModel());
                    else {
                        return -1;
                    }
                })
                .map(this::persistenceToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CarDto save(Car car) {
        Car carSaved = this.carRepository.save(car);
        return this.persistenceToDto(carSaved);
    }

    private Car findByUserAndId(User user, Long id) {
        return this.carRepository.findByUserAndId(user, id).orElseThrow(() -> new NotFoundException("Car"));
    }
}
