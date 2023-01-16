package nextstep.exception;

import nextstep.auth.UnAuthorizationException;
import nextstep.support.DuplicateEntityException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException() {
        return ResponseEntity.badRequest().body("인증에 실패했습니다.");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<String> handleDuplicateEntityException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<String> handleUnAuthorizationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
    }
}
