package nextstep.reservations.exceptions.member;

import nextstep.reservations.exceptions.member.exception.NotExistMemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(value = NotExistMemberException.class)
    public ResponseEntity<String> notExistMember(NotExistMemberException e) {
        return ResponseEntity
                .badRequest()
                .body("존재하지 않는 사용자입니다.");
    }
}
