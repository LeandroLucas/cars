package com.company.carsapi.controllers;


import com.company.carsapi.config.CheckAuthorization;
import com.company.carsapi.models.transport.response.CarDto;
import com.company.carsapi.services.ImageUploadService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ImageUploadController {
    private final ImageUploadService imageUploadService;

    public ImageUploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @CheckAuthorization
    @PostMapping(path = "/cars/{id}/img", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> uploadCarImage(@RequestParam("file") MultipartFile file,
                                                 @PathVariable("id") Long carId,
                                                 @RequestHeader(required = false, name = HttpHeaders.AUTHORIZATION) String token) {
        CarDto car = this.imageUploadService.uploadCarImage(token, file, carId);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }
}
