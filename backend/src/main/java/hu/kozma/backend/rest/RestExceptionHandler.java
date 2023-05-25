package hu.kozma.backend.rest;

import hu.kozma.backend.exceptions.AnnounceDateConflict;
import hu.kozma.backend.exceptions.UserNotLoggedInException;
import hu.kozma.backend.exceptions.UsernameAlreadyTakenException;
import hu.kozma.backend.exceptions.WrongPasswordException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> buildResponseEntity(RestError restError) {
		return new ResponseEntity<>(restError, restError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		RestError restError = new RestError(HttpStatus.BAD_REQUEST);
		restError.setError(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
		return buildResponseEntity(restError);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> generalException(Exception ex) {
		RestError restError = new RestError(HttpStatus.INTERNAL_SERVER_ERROR);
		restError.setError(ex.getMessage());
		return buildResponseEntity(restError);
	}

	@ExceptionHandler(WrongPasswordException.class)
	protected ResponseEntity<Object> wrongPasswordException(WrongPasswordException ex) {
		RestError restError = new RestError(HttpStatus.UNAUTHORIZED);
		restError.setError(ex.getMessage());
		return buildResponseEntity(restError);
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
		restError.setError("Hiányzó entitás.");
		return buildResponseEntity(restError);
	}

	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	protected ResponseEntity<Object> authenticationError(AuthenticationCredentialsNotFoundException ex) {
		RestError restError = new RestError(HttpStatus.UNAUTHORIZED);
		restError.setError(ex.getMessage());
		return buildResponseEntity(restError);
	}

	@ExceptionHandler(UsernameAlreadyTakenException.class)
	protected ResponseEntity<Object> usernameAlreadyTaken(UsernameAlreadyTakenException ex) {
		RestError restError = new RestError(HttpStatus.BAD_REQUEST);
		restError.setError(ex.getMessage());
		return buildResponseEntity(restError);
	}

	@ExceptionHandler(AnnounceDateConflict.class)
	protected ResponseEntity<Object> announceDateConflict(AnnounceDateConflict ex) {
		RestError restError = new RestError(HttpStatus.BAD_REQUEST);
		restError.setError(ex.getMessage());
		return buildResponseEntity(restError);
	}

	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<Object> badCredentialsException(BadCredentialsException ex) {
		RestError restError = new RestError(HttpStatus.BAD_REQUEST);
		restError.setError("A jelszó nem megfelelő.");
		return buildResponseEntity(restError);
	}

	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<Object> authenticationException(AuthenticationException ex) {
		RestError restError = new RestError(HttpStatus.FORBIDDEN);
		restError.setError("Ez a funkció csak bejelentkezett felhasználóknak elérhető.");
		return buildResponseEntity(restError);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		RestError restError = new RestError(HttpStatus.BAD_REQUEST);
		restError.setError("Hiányzó paraméter.");
		return buildResponseEntity(restError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		RestError restError = new RestError(HttpStatus.BAD_REQUEST);
		restError.setError("Hibás lekérdezés.");
		return buildResponseEntity(restError);
	}
}
