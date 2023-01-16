package nextstep.support;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_AUTHORIZED("인증되지 않은 회원입니다.", HttpStatus.UNAUTHORIZED),
    DUPLICATE_RESERVATION("중복된 예약입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXISTS_MEMBER("존재하지 않는 회원입니다.", HttpStatus.UNAUTHORIZED),
    PASSWORD_NOT_MATCH("비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus httpStatus;
}
