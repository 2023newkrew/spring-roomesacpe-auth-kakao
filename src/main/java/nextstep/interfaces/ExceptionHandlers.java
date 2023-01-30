package nextstep.interfaces;

import nextstep.interfaces.exception.AuthorizationException;
import nextstep.interfaces.exception.DuplicateEntityException;
import nextstep.interfaces.exception.NotExistEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(NotExistEntityException.class)
    public ResponseEntity onException(NotExistEntityException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity onException(DuplicateEntityException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity onException(AuthorizationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
