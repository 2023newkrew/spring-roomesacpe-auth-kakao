package nextstep.error;

import nextstep.error.exception.AuthenticationException;
import nextstep.error.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        return getResponseEntity(e);
    }

    private ResponseEntity<ErrorResponse> getResponseEntity(CustomException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
