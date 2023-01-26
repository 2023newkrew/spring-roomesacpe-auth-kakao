package nextstep.reservations.exceptions.auth;

import nextstep.reservations.exceptions.auth.exception.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseEntity<String> authrozation(AuthorizationException e) {
        return ResponseEntity
                .badRequest()
                .body("권한이 없습니다.");
    }
}
