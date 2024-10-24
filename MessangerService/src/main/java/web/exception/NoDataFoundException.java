package web.exception;

public class NoDataFoundException extends NullPointerException {
    public NoDataFoundException(String reason) {
        super(reason);
    }
}
