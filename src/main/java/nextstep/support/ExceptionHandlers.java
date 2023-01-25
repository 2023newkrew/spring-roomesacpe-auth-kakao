package nextstep.support;

import com.sun.jdi.request.DuplicateRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.naming.NotContextException;

@RestControllerAdvice
public class ExceptionHandlers {
    private static final Logger logger =
            LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(NotContextException.class)
    public ResponseEntity<Object> badRequestException(final NotContextException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class )
    public ResponseEntity<Object> lengthRequiredException(final NullPointerException ex) {
        logger.warn(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.LENGTH_REQUIRED);
    }

    @ExceptionHandler(AuthorizationServiceException.class )
    public ResponseEntity<Object> unAuthorizedException(final AuthorizationServiceException ex) {
        logger.warn(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler({DuplicateRequestException.class, KeyAlreadyExistsException.class})
    public ResponseEntity<Object> duplicateException(final RuntimeException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}