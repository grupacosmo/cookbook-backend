package pl.edu.pk.cosmo.cookbookbackend.service.exception;

/**
 * @author Patryk Borchowiec
 */
public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
