package com.company.carsapi.services;

import com.company.carsapi.exceptions.ImageUploadException;
import com.company.carsapi.exceptions.NotFoundException;
import com.company.carsapi.models.persistence.Car;
import com.company.carsapi.models.persistence.Session;
import com.company.carsapi.models.transport.response.CarDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class ImageUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUploadService.class);

    @Value("${upload.dir}")
    private String uploadDir;
    private final AuthService authService;
    private final CarService carService;

    public ImageUploadService(AuthService authService, CarService carService) {
        this.authService = authService;
        this.carService = carService;
    }

    public CarDto uploadCarImage(String token, MultipartFile file, Long id) {
        Session session = this.authService.checkSession(token);
        Car car = session.getUser().getCars()
                .stream().filter(c -> Objects.equals(c.getId(), id))
                .findAny().orElseThrow(() -> new NotFoundException("Car"));
        try {
            this.deleteOldImageIfExists(car);
            // Verifica se o diretório de upload existe, se não, cria
            Path uploadPath = Path.of(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            if (!fileExtension.equals("jpg") && !fileExtension.equals("png")) {
                throw new ImageUploadException("Invalid Image Format");
            }
            String fileName = "image-car-" + id + "." + fileExtension;
            car.setImageName(fileName);
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return this.carService.save(car);
        } catch (Exception e) {
            LOGGER.error("Image Upload Error", e);
            throw new ImageUploadException();
        }
    }

    private boolean deleteOldImageIfExists(Car car) {
        if (car.getImageName() != null) {
            Path uploadPath = Path.of(uploadDir);
            Path filePath = uploadPath.resolve(car.getImageName());
            return filePath.toFile().delete();
        }
        return false;
    }
}
