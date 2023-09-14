package com.company.carsapi.controllers;

import com.company.carsapi.models.transport.request.EditUser;
import com.company.carsapi.models.transport.response.UserDto;
import com.company.carsapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> create(@Valid @RequestBody EditUser create)  {
        UserDto user = this.userService.create(create);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<UserDto>> list()  {
        Iterable<UserDto> list = this.userService.list();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> get(@PathVariable("id") Long id)  {
        UserDto user = this.userService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)  {
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody EditUser update)  {
        this.userService.update(id, update);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
