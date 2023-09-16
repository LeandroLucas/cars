package com.company.carsapi.config;

import com.company.carsapi.constants.ValidationConstants;
import com.company.carsapi.exceptions.*;
import com.company.carsapi.models.transport.response.ErrorResponse;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler
        extends ResponseEntityExceptionHandler {

    private final static String UNIQUE_INDEX_VIOLATION = "Unique index or primary key violation:";

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {

        String message = ValidationConstants.MISSING_FIELDS_MESSAGE;
        if (ex.getCause() instanceof ConstraintViolationException constraintViolationException) {
            JdbcSQLIntegrityConstraintViolationException cause = (JdbcSQLIntegrityConstraintViolationException) constraintViolationException.getCause();
            String causeMessage = cause.getMessage();
            if (causeMessage.startsWith(UNIQUE_INDEX_VIOLATION)) {
                String fieldName = this.getFieldName(causeMessage);
                message = fieldName + " already exists";
            }
        }

        ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.status(apiError.getErrorCode()).body(apiError);
    }

    @ExceptionHandler(value = {NotFoundException.class,
            AuthenticationException.class,
            AuthorizationException.class,
            EncryptPasswordException.class,
            ImageUploadException.class})
    protected ResponseEntity<ErrorResponse> handleCustomExceptions(
            AbstractException ex, WebRequest request) {
        ErrorResponse apiError = new ErrorResponse(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity.status(apiError.getErrorCode()).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        String message = "";
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            message = fieldError.getDefaultMessage();
            break;
        }

        ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.status(apiError.getErrorCode()).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ValidationConstants.INVALID_FIELDS_MESSAGE);
        return ResponseEntity.status(apiError.getErrorCode()).body(apiError);
    }

    private String getFieldName(String message) {
        int start = message.indexOf("(") + 1;
        int end = message.indexOf(")");
        return message.substring(start, end).split(" ")[0].toLowerCase();
    }
}
