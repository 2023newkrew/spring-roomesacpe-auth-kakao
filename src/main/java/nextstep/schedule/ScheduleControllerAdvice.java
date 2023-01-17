package nextstep.schedule;

import nextstep.exception.NotExistEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScheduleControllerAdvice {
    @ExceptionHandler(NotExistEntityException.class)
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().build();
    }
}
