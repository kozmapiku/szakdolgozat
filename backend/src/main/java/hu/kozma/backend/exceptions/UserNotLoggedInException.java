package hu.kozma.backend.exceptions;

public class UserNotLoggedInException extends Exception{
    public UserNotLoggedInException(String errorMessage) {
        super(errorMessage);
    }
}
