package com.company.carsapi.services;

import com.company.carsapi.models.persistence.Car;
import com.company.carsapi.models.persistence.Session;
import com.company.carsapi.models.transport.request.EditCar;
import com.company.carsapi.models.transport.response.CarDto;
import com.company.carsapi.repositories.CarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private CarService carService;

    @Test
    public void createCarSuccess() {
        EditCar carToCreate = this.buildCar();
        String fakeToken = "fake-token";
        Mockito.when(this.authService.checkSession(fakeToken)).thenReturn(this.fakeSession());
        Mockito.when(this.carRepository.save(Mockito.any()))
                .thenAnswer(invocation -> {
                    Car car = invocation.getArgument(0);
                    car.setId(1L);
                    return car;
                });
        CarDto response = this.carService.createCar(fakeToken, carToCreate);

        Mockito.verify(this.authService).checkSession(fakeToken);
        assertEquals(carToCreate.getLicensePlate(), response.getLicensePlate());
        assertEquals(1L, response.getId());
        assertEquals(carToCreate.getModel(), response.getModel());
        assertEquals(carToCreate.getYear(), response.getYear());
        assertEquals(carToCreate.getColor(), response.getColor());
    }

    @Test
    public void createCarPersistenceException() {
        EditCar carToCreate = this.buildCar();
        String fakeToken = "fake-token";
        Mockito.when(this.authService.checkSession(fakeToken)).thenReturn(this.fakeSession());
        Mockito.when(this.carRepository.save(Mockito.any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> this.carService.createCar(fakeToken, carToCreate));
        Mockito.verify(this.authService).checkSession(fakeToken);
    }

    @Test
    public void updateCarSuccess() {
        EditCar dataToUpdate = this.buildCar();
        String fakeToken = "fake-token";
        Long carId = 1L;
        Mockito.when(this.authService.checkSession(fakeToken)).thenReturn(this.fakeSession());
        Mockito.when(this.carRepository.findByUserAndId(Mockito.any(), Mockito.anyLong()))
                .thenReturn(Optional.of(new Car()));
        this.carService.updateBySessionAndId(fakeToken, carId, dataToUpdate);

        Mockito.verify(this.carRepository).findByUserAndId(Mockito.any(), Mockito.anyLong());
        Mockito.verify(this.authService).checkSession(fakeToken);
    }

    private EditCar buildCar() {
        EditCar car = new EditCar();
        car.setColor("Black");
        car.setModel("Honda City");
        car.setYear(2020);
        car.setLicensePlate("RST-1234");
        return car;
    }

    private Session fakeSession() {
        return new Session();
    }


}
