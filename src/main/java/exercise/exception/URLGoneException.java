package exercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class URLGoneException extends RuntimeException {
    public URLGoneException(String message) {
        super(message);
    }
}
