package com.company.carsapi.services;

import com.company.carsapi.exceptions.AuthenticationException;
import com.company.carsapi.exceptions.NotFoundException;
import com.company.carsapi.models.persistence.Session;
import com.company.carsapi.models.transport.request.AuthUser;
import com.company.carsapi.models.transport.request.EditCar;
import com.company.carsapi.models.transport.request.CreateUser;
import com.company.carsapi.models.persistence.Car;
import com.company.carsapi.models.persistence.User;
import com.company.carsapi.models.transport.request.EditUser;
import com.company.carsapi.models.transport.response.PrivateUserDto;
import com.company.carsapi.models.transport.response.SessionDto;
import com.company.carsapi.models.transport.response.UserDto;
import com.company.carsapi.repositories.UserRepository;
import com.company.carsapi.utils.CryptUtils;
import jakarta.transaction.Transactional;
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
    private final AuthService authService;

    public UserService(UserRepository userRepository, CarService carService, AuthService authService) {
        this.userRepository = userRepository;
        this.carService = carService;
        this.authService = authService;
    }

    @Transactional
    public UserDto create(CreateUser createUser) {
        User user = new User();
        this.applyToUser(user, createUser);
        if (!CollectionUtils.isEmpty(createUser.getCars())) {
            List<Car> cars = user.getCars();
            for (EditCar car : createUser.getCars()) {
                cars.add(this.carService.preparePersistenceCar(user, car));
            }
        }
        user = this.userRepository.save(user);
        return this.persistenceToDto(user);
    }

    public List<UserDto> list() {
        List<User> users = this.userRepository.findAllOrdered();
        return users.stream()
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
        this.applyToUser(user, userData);
        this.userRepository.save(user);
    }

    public PrivateUserDto getBySession(String token) {
        Session session = this.authService.checkSession(token);
        PrivateUserDto privateUserDto = this.persistenceToPrivateDto(session.getUser());
        privateUserDto.setLastLogin(session.getCreatedAt());
        return privateUserDto;
    }

    @Transactional
    public SessionDto signIn(AuthUser auth) {
        String encryptedPass = CryptUtils.encryptPassword(auth.getLogin(), auth.getPassword());
        User user = this.findByCredentials(auth.getLogin(), encryptedPass)
                .orElseThrow(AuthenticationException::new);
        Session session = this.authService.buildSession(user);
        return this.authService.persistenceToDto(session);
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
    }

    /**
     * Converte objeto de persistencia do usuário para o objeto de transporte
     *
     * @param user Usuário que será convertido
     * @return UserDto com as informações do usuário
     */
    private UserDto persistenceToDto(User user) {
        UserDto dto = new UserDto();
        this.applyToDto(dto, user);
        return dto;
    }

    public PrivateUserDto persistenceToPrivateDto(User user) {
        PrivateUserDto dto = new PrivateUserDto();
        this.applyToDto(dto, user);
        dto.setCreatedAt(user.getCreatedAt());
        dto.setPhone(user.getPhone());
        dto.setBirthday(user.getBirthday());
        return dto;
    }

    private void applyToDto(UserDto dto, User user) {
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
    }
}
