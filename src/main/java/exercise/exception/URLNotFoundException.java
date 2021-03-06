package exercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class URLNotFoundException extends RuntimeException {
    public URLNotFoundException(String message) {
        super(message);
    }
}
