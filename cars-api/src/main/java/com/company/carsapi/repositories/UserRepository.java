package com.company.carsapi.repositories;

import com.company.carsapi.models.persistence.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u, sum(c.usageCounter) as carUsages FROM User as u LEFT JOIN Car as c ON c.user.id=u.id GROUP BY u.id ORDER BY carUsages DESC, u.login")
    List<User> findAllOrdered();

    Optional<User> findByLoginAndPassword(String login, String password);
}
