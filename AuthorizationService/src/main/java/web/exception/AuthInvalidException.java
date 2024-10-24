package web.exception;

public class AuthInvalidException extends RuntimeException {
    public AuthInvalidException(String message) {
        super(message);
    }
}
