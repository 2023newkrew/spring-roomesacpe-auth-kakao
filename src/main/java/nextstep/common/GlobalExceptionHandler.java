package nextstep.common;

import io.jsonwebtoken.JwtException;
import nextstep.common.exception.DuplicateEntityException;
import nextstep.common.exception.NoAccessTokenException;
import nextstep.common.exception.NotExistEntityException;
import nextstep.common.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<String> duplicateEntityExceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(exception.getMessage());
    }

    @ExceptionHandler({NoAccessTokenException.class, JwtException.class})
    public ResponseEntity<String> accessTokenExceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> unauthorizedExceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(exception.getMessage());
    }

    @ExceptionHandler(NotExistEntityException.class)
    public ResponseEntity<String> notExistEntityExceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }
}
