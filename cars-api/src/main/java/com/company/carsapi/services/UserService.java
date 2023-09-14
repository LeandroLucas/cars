package com.company.carsapi.services;

import com.company.carsapi.exceptions.NotFoundException;
import com.company.carsapi.models.transport.request.EditCar;
import com.company.carsapi.models.transport.request.EditUser;
import com.company.carsapi.models.persistence.Car;
import com.company.carsapi.models.persistence.User;
import com.company.carsapi.models.transport.response.UserDto;
import com.company.carsapi.repositories.UserRepository;
import com.company.carsapi.utils.CryptUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe responsável por manipular a tabela 'User'
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    private final CarService carService;

    public UserService(UserRepository userRepository, CarService carService) {
        this.userRepository = userRepository;
        this.carService = carService;
    }

    @Transactional
    public UserDto create(EditUser createUser) {
        User user = new User();
        this.applyToUser(user, createUser);
        user = this.userRepository.save(user);
        return this.persistenceToDto(user);
    }

    public Iterable<UserDto> list() {
        Iterable<User> users = this.userRepository.findAllOrdered();
        return StreamUtils.createStreamFromIterator(users.iterator())
                .map(this::persistenceToDto).collect(Collectors.toList());
    }

    public UserDto get(Long id) {
        User user = this.find(id);
        return this.persistenceToDto(user);
    }

    public User find(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
    }

    public Optional<User> findByCredentials(String login, String encryptedPass) {
        return this.userRepository.findByLoginAndPassword(login, encryptedPass);
    }

    @Transactional
    public void delete(Long id) {
        User user = this.find(id);
        this.userRepository.delete(user);
    }

    @Transactional
    public void update(Long id, EditUser userData) {
        User user = this.find(id);
        this.carService.deleteAll(user.getCars());
        this.applyToUser(user, userData);
        this.userRepository.save(user);
    }

    /**
     * Aplica no user as informações do EditUser
     *
     * @param user     Objeto onde as informações serão setadas
     * @param userData Objeto de onde as informações serão coletadas
     */
    private void applyToUser(User user, EditUser userData) {
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setBirthday(userData.getBirthday());
        user.setEmail(userData.getEmail());
        user.setLogin(userData.getLogin());
        user.setPassword(CryptUtils.encryptPassword(userData.getLogin(), userData.getPassword())); //FIXME criptografar
        user.setPhone(userData.getPhone());
        if (!CollectionUtils.isEmpty(userData.getCars())) {
            List<Car> cars = user.getCars();
            cars.clear();
            for (EditCar car : userData.getCars()) {
                cars.add(this.carService.preparePersistenceCar(user, car));
            }
        }
    }

    /**
     * Converte objeto de persistencia do usuário para o objeto de transporte
     *
     * @param user Usuário que será convertido
     * @return UserDto com as informações do usuário
     */
    private UserDto persistenceToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setPhone(user.getPhone());
        dto.setBirthday(user.getBirthday());
        dto.setCars(user.getCars().stream()
                .sorted((cara, carb) -> {
                    if (cara.getUsageCounter() > carb.getUsageCounter())
                        return 1;
                    else if (cara.getUsageCounter() == carb.getUsageCounter())
                        return cara.getModel().compareToIgnoreCase(carb.getModel());
                    else {
                        return -1;
                    }
                })
                .map(this.carService::persistencetoDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
