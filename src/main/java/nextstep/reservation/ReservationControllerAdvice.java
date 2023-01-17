package nextstep.reservation;

import nextstep.exception.DuplicateEntityException;
import nextstep.exception.InvalidAuthorizationTokenException;
import nextstep.exception.NotExistEntityException;
import nextstep.exception.NotQualifiedMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("nextstep.reservation")
public class ReservationControllerAdvice {
    @ExceptionHandler({NotExistEntityException.class,
            DuplicateEntityException.class,
            InvalidAuthorizationTokenException.class})
    public ResponseEntity handleBadRequest() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotQualifiedMemberException.class)
    public ResponseEntity handleUnauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
