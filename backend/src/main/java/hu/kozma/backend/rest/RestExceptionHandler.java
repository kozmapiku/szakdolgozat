package hu.kozma.backend.rest;

import hu.kozma.backend.exceptions.UserNotLoggedInException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(RestError restError) {
        return new ResponseEntity<>(restError, restError.getStatus());
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    protected ResponseEntity<Object> handleUserNotLoggedIn(UserNotLoggedInException ex) {
        RestError restError = new RestError(HttpStatus.METHOD_NOT_ALLOWED);
        restError.setError(ex.getMessage());
        return buildResponseEntity(restError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> entityNotFound(EntityNotFoundException ex) {
        RestError restError = new RestError(HttpStatus.NOT_FOUND);
        restError.setError(ex.getMessage());
        return buildResponseEntity(restError);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    protected ResponseEntity<Object> authenticationError(AuthenticationCredentialsNotFoundException ex) {
        RestError restError = new RestError(HttpStatus.UNAUTHORIZED);
        restError.setError(ex.getMessage());
        return buildResponseEntity(restError);
    }
}
