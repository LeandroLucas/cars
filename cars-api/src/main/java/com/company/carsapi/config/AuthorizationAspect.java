package com.company.carsapi.config;

import com.company.carsapi.exceptions.AuthorizationException;
import com.company.carsapi.models.persistence.Session;
import com.company.carsapi.services.AuthService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * Aspect responsável por interceptar as chamadas dos
 * métodos que contém a anotação @CheckAuthorization,
 * capturar o 'token' entre os parametros e realizar validação da sessão
 */
@Aspect
@Configuration
public class AuthorizationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationAspect.class);

    private final AuthService authService;

    public AuthorizationAspect(AuthService authService) {
        this.authService = authService;
    }

    @Around("authorizeAnnotationPointcut()")
    public Object authorize(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String token = "unknown";
        try {
            token = this.findParameterValue("token", signature.getParameterNames(),
                    proceedingJoinPoint.getArgs()).orElseThrow();

            Session validSession = this.authService.checkSession(token);

            LOGGER.debug("Session " + validSession.getId() + " authorized");
            return proceedingJoinPoint.proceed();
        } catch (AuthorizationException e) {
            LOGGER.debug(token + " unauthorized", e);
            throw e;
        } catch (Exception e) {
            LOGGER.debug(token + " unauthorized", e);
            throw new AuthorizationException();
        }
    }

    @Pointcut("@annotation(com.company.carsapi.config.CheckAuthorization)")
    public void authorizeAnnotationPointcut() {
    }

    private Optional<String> findParameterValue(String parameterName, String[] parameterNames,
                                                Object[] parameterValues) {
        String parameterValue = null;
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equalsIgnoreCase(parameterName)) {
                parameterValue = (String) parameterValues[i];
                break;
            }
        }
        return Optional.ofNullable(parameterValue);
    }
}
