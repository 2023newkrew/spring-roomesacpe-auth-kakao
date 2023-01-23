package nextstep.support;

import com.sun.jdi.request.DuplicateRequestException;
import nextstep.auth.authorization.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.naming.NotContextException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandlers {
    private static final Logger logger =
            LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler({DuplicateRequestException.class, ArithmeticException.class})
    public ResponseEntity<Object> badRequestException(final RuntimeException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> notFoundException(final NoSuchElementException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class )
    public ResponseEntity<Object> lengthRequiredException(final NullPointerException ex) {
        logger.warn(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.LENGTH_REQUIRED);
    }

    @ExceptionHandler(AuthorizationException.class )
    public ResponseEntity<Object> unAuthorizedException(final AuthorizationException ex) {
        logger.warn(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({KeyAlreadyExistsException.class, NotContextException.class})
    public ResponseEntity<Object> remainElement(final Exception ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}