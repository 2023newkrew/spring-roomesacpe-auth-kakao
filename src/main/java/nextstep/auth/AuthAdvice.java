package nextstep.auth;

import nextstep.support.InvalidLoginException;
import nextstep.support.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthAdvice {

    @ExceptionHandler({InvalidLoginException.class, UnauthorizedException.class})
    public ResponseEntity<String> onException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
