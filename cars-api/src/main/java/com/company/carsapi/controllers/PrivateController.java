package com.company.carsapi.controllers;

import com.company.carsapi.config.CheckAuthorization;
import com.company.carsapi.models.transport.request.EditCar;
import com.company.carsapi.models.transport.response.CarDto;
import com.company.carsapi.models.transport.response.PrivateUserDto;
import com.company.carsapi.services.CarService;
import com.company.carsapi.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PrivateController {

    private final UserService userService;
    private final CarService carService;

    public PrivateController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    @CheckAuthorization
    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrivateUserDto> get(@RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String token) {
        PrivateUserDto user = this.userService.getBySession(token);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @CheckAuthorization
    @GetMapping(path = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarDto>> listCars(@RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String token) {
        List<CarDto> car = this.carService.listBySession(token);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @CheckAuthorization
    @PostMapping(path = "/cars", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> createCar(@RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String token,
                                            @RequestBody EditCar carToCreate) {
        CarDto car = this.carService.createCar(token, carToCreate);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @CheckAuthorization
    @GetMapping(path = "/cars/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> getCar(@RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String token,
                                         @PathVariable("id") Long id) {
        CarDto car = this.carService.getBySessionAndId(token, id);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @CheckAuthorization
    @DeleteMapping(path = "/cars/{id}")
    public ResponseEntity<CarDto> deleteCar(@RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String token,
                                         @PathVariable("id") Long id) {
        this.carService.deleteBySessionAndId(token, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @CheckAuthorization
    @PutMapping(path = "/cars/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> updateCar(@RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String token,
                                            @PathVariable("id") Long id,
                                            @RequestBody EditCar carToUpdate) {
        this.carService.updateBySessionAndId(token, id, carToUpdate);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
