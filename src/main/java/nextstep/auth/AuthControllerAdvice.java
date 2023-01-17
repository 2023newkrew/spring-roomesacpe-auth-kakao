package nextstep.auth;

import nextstep.exception.NotCorrectPasswordException;
import nextstep.exception.NotExistEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("nextstep.auth")
public class AuthControllerAdvice {
    @ExceptionHandler(value = {NotExistEntityException.class, NotCorrectPasswordException.class})
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().build();
    }
}
