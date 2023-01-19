package nextstep.support;

import nextstep.support.exception.RoomEscapeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RoomEscapeException.class)
    public ResponseEntity onException(RoomEscapeException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
