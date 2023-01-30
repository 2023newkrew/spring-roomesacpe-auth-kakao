package nextstep.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_AUTHENTICATED("인증되지 않은 회원입니다.", HttpStatus.UNAUTHORIZED),
    NOT_RESERVATION_OWNER("본인의 예약이 아니면 삭제할 수 없습니다.", HttpStatus.FORBIDDEN),
    DUPLICATE_RESERVATION("중복된 예약입니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH("비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED),
    NO_SUCH_MEMBER("존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND),
    NO_SUCH_THEME("없는 테마입니다.", HttpStatus.NOT_FOUND),
    NO_SUCH_RESERVATION("없는 예약입니다.", HttpStatus.NOT_FOUND),
    INVALID_TOKEN("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    ADMIN_ONLY("관리자만 요청 가능한 기능입니다.", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus httpStatus;
}
