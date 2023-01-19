package nextstep.exception;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NotExistEntityException.class)
    public ResponseEntity<String> handleNotExistEntityException() {
        return ResponseEntity.badRequest().body("존재하지 않는 값입니다.");
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<String> handleDuplicateEntityException() {
        return ResponseEntity.badRequest().body("중복되는 값입니다.");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException() {
        return ResponseEntity.badRequest().body("인증에 실패했습니다.");
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<String> handleUnAuthorizationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
    }

    @ExceptionHandler(InaccessibleReservationException.class)
    public ResponseEntity<String> handleInaccessibleReservationException() {
        return ResponseEntity.badRequest().body("접근할 수 없는 예약 정보입니다.");
    }
}
