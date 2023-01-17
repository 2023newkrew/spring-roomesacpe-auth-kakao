package nextstep.theme;

import nextstep.exception.NotExistEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ThemeControllerAdvice {
    @ExceptionHandler(NotExistEntityException.class)
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().build();
    }
}
