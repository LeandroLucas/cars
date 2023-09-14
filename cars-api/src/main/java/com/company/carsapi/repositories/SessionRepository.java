package com.company.carsapi.repositories;

import com.company.carsapi.models.persistence.Session;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Long> {

    Optional<Session> findByTokenAndExpireAtGreaterThanAndLogoutAtIsNull(String token, LocalDateTime now);
}
