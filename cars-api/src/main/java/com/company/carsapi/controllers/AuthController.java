package com.company.carsapi.controllers;

import com.company.carsapi.config.CheckAuthorization;
import com.company.carsapi.models.transport.request.AuthUser;
import com.company.carsapi.models.transport.response.SessionDto;
import com.company.carsapi.services.AuthService;
import com.company.carsapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionDto> login(@Valid @RequestBody AuthUser auth) {
        SessionDto session = this.userService.signIn(auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @CheckAuthorization
    @DeleteMapping(path = "/signout")
    public ResponseEntity<SessionDto> logout(@Valid @RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String token) {
        this.authService.signOut(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
