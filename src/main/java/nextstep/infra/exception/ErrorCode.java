package nextstep.infra.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Authentication Error (1000 ~)
    NOT_AUTHORIZED("인증되지 않은 회원입니다.", 1000, HttpStatus.UNAUTHORIZED),
    PASSWORD_NOT_MATCH("비밀번호가 틀렸습니다.", 1001, HttpStatus.UNAUTHORIZED),
    NO_SUCH_TOKEN("토큰이 존재하지 않습니다.", 1002, HttpStatus.UNAUTHORIZED),
    NO_SUCH_MEMBER("존재하지 않는 회원입니다.", 1003, HttpStatus.NOT_FOUND),
    NO_SUCH_ROLE("존재하지 않는 역할입니다.", 1004, HttpStatus.NOT_FOUND),
    NO_ACCESS_AUTHORITY("존재하지 않는 역할입니다.", 1005, HttpStatus.FORBIDDEN),


    // Api Error (2000 ~)
    NOT_RESERVATION_OWNER("본인의 예약이 아니면 삭제할 수 없습니다.", 2000, HttpStatus.FORBIDDEN),
    DUPLICATE_RESERVATION("중복된 예약입니다.", 2001, HttpStatus.BAD_REQUEST),
    NO_SUCH_THEME("없는 테마입니다.", 2002, HttpStatus.NOT_FOUND),
    NO_SUCH_RESERVATION("없는 예약입니다.", 2003, HttpStatus.NOT_FOUND);

    private final String message;
    private final int errorCode;
    private final HttpStatus httpStatus;
}
