package nextstep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Void> onUnauthorizedAccessException(UnauthorizedAccessException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(NotExistEntityException.class)
    public ResponseEntity<Void> onNotExistEntityException(NotExistEntityException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<Void> onDuplicateEntityException(DuplicateEntityException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> onIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
