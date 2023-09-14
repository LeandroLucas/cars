package com.company.carsapi.controllers;

import com.company.carsapi.config.CheckAuthorization;
import com.company.carsapi.models.transport.request.AuthUser;
import com.company.carsapi.models.transport.response.SessionDto;
import com.company.carsapi.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionDto> login(@Valid @RequestBody AuthUser auth) {
        SessionDto session = this.authService.signIn(auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @CheckAuthorization
    @DeleteMapping(path = "/signout")
    public ResponseEntity<SessionDto> logout(@Valid @RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String token) {
        this.authService.signOut(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
