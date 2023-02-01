package nextstep.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({
            InvalidLoginException.class, DuplicateEntityException.class, NotExistEntityException.class
    })
    public ResponseEntity<String> onBadRequest(Exception e) {
        LOGGER.debug(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> onUnauthorized(Exception e) {
        LOGGER.debug(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> onForbidden(Exception e) {
        LOGGER.debug(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> onInternalServerError(Exception e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity.internalServerError().body("요청을 처리할 수 없습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOGGER.debug(e.getMessage());
        return ResponseEntity.badRequest().body(e.getFieldError().getDefaultMessage());
    }
}
