package nextstep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<Void> handleException(CustomException e) {
        return ResponseEntity
                .status(HttpStatus.valueOf(e.getStatusCode()))
                .header("message", e.getMessage()).build();
    }
}
