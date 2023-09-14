package com.company.carsapi.controllers;

import com.company.carsapi.config.CheckAuthorization;
import com.company.carsapi.models.transport.response.PrivateUserDto;
import com.company.carsapi.services.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class PrivateController {

    private final AuthService authService;

    public PrivateController(AuthService authService) {
        this.authService = authService;
    }

    @CheckAuthorization
    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrivateUserDto> get(@RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String token) {
        PrivateUserDto user = this.authService.getUserBySession(token);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
