package nextstep.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RoomEscapeExceptionHandler {

    @ExceptionHandler(RoomEscapeException.class)
    public ResponseEntity<String> handleRoomEscapeException(RoomEscapeException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
