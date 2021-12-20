package pl.edu.pk.cosmo.cookbookbackend.service.exception;

public class NoAccountException extends Exception{
    public NoAccountException(String message) {
        super(message);
    }

    public NoAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
