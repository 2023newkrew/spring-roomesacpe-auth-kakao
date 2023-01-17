package nextstep.member;

import nextstep.exception.InvalidAuthorizationTokenException;
import nextstep.exception.NotExistEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("nextstep.member")
public class MemberControllerAdvice {
    @ExceptionHandler(value = {NotExistEntityException.class, InvalidAuthorizationTokenException.class})
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().build();
    }
}
