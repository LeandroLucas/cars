package com.company.carsapi.scheduled;

import com.company.carsapi.models.persistence.Session;
import com.company.carsapi.services.AuthService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledLogout {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledLogout.class);

    private final AuthService authService;

    public ScheduledLogout(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Seta o logoutAt das sessões expiradas
     */
    @Transactional
    @Scheduled(cron = "0 0/1 * * * ?") // Executa a cada 1 minuto
    public void scheduledLogout() {
        LocalDateTime now = LocalDateTime.now();
        List<Session> sessions = this.authService.findByExpireAtLessThanAndLogoutAtIsNull(now);
        sessions.forEach(session -> session.setLogoutAt(now));
        this.authService.saveSessions(sessions);
        LOGGER.debug("Tarefa agendada executada " + new Date());
    }
}
