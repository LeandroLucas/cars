package com.company.carsapi.services;

import com.company.carsapi.exceptions.AuthorizationException;
import com.company.carsapi.models.persistence.Session;
import com.company.carsapi.models.persistence.User;
import com.company.carsapi.models.transport.response.SessionDto;
import com.company.carsapi.repositories.SessionRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Serviço responsável pelas regras de autenticação
 * e por manipular a tabela 'Session'
 */
@Service
public class AuthService {

    private final SecretKey KEY = Keys.hmacShaKeyFor(
            "eyJzdWIiOiJoZWxsby53b3JsZCIsImlhdCI6MTY5NDY5OTU1MSwiZXhwIjoxNjk0"
                    .getBytes(StandardCharsets.UTF_8));

    private static final int SESSION_DURATION_MINUTES = 60;

    private final SessionRepository sessionRepository;

    public AuthService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session checkSession(String token) {
        return this.sessionRepository.findByTokenAndExpireAtGreaterThanAndLogoutAtIsNull(token, LocalDateTime.now())
                .orElseThrow(() -> new AuthorizationException("Unauthorized - invalid session"));
    }

    @Transactional
    public void signOut(String token) {
        Session session = this.checkSession(token);
        session.setLogoutAt(LocalDateTime.now());
        this.sessionRepository.save(session);
    }

    /**
     * Converte objeto de persistencia do usuário para o objeto de transporte
     *
     * @param session Sessão que será convertida
     * @return SessionDto com as informações da sessão
     */
    public SessionDto persistenceToDto(Session session) {
        SessionDto dto = new SessionDto();
        dto.setToken(session.getToken());
        return dto;
    }

    /**
     * Gera uma sessão no banco para o usuário informado
     *
     * @param user Usuário dono da sessão que será gerada
     * @return Sessão criada para o usuário
     */
    public Session buildSession(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setExpireAt(LocalDateTime.now().plusMinutes(SESSION_DURATION_MINUTES));
        session.setToken(this.buildJWTToken(user, session.getExpireAt()));
        this.sessionRepository.save(session);
        return session;
    }

    public List<Session> findByExpireAtLessThanAndLogoutAtIsNull(LocalDateTime date) {
        return this.sessionRepository.findByExpireAtLessThanAndLogoutAtIsNull(date);
    }

    public void saveSessions(List<Session> sessions) {
        this.sessionRepository.saveAll(sessions);
    }

    private String buildJWTToken(User user, LocalDateTime expireAt) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(KEY, SignatureAlgorithm.HS512)
                .compact();
    }
}
