package nextstep.exception;

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

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<String> handleUnAuthorizationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
    }
}
