package it.qbteam.exception;

public class AuthenticationException extends Throwable {

    private static final long serialVersionUID = 1L;

    private final String message;

    public AuthenticationException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AuthenticationException: " + message;
    }
}