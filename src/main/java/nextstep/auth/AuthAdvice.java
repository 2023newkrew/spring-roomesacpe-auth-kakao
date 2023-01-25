package nextstep.auth;

import nextstep.support.InvalidLoginException;
import nextstep.support.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthAdvice.class);

    @ExceptionHandler({InvalidLoginException.class, UnauthorizedException.class})
    public ResponseEntity<String> onException(Exception e) {
        LOGGER.debug(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
