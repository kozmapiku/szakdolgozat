package hu.kozma.backend.exceptions;

public class UsernameAlreadyTakenException extends Exception {
	public UsernameAlreadyTakenException(String errorMessage) {
		super(errorMessage);
	}
}
