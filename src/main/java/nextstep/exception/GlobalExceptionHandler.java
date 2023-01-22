package nextstep.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NullPointerException.class, DuplicateEntityException.class})
    public ResponseEntity<Void> onNPEAndDuplicatedEntityException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
