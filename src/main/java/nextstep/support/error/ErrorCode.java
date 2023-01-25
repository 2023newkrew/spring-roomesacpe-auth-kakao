package nextstep.support.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATE_THEME(HttpStatus.BAD_REQUEST, "동일한 이름의 테마가 존재합니다."),
    DUPLICATE_RESERVATION(HttpStatus.BAD_REQUEST, "동일한 날짜와 시간에 예약이 존재합니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    NOT_EXISTS_RESERVATION(HttpStatus.NOT_FOUND, "해당 예약이 존재하지 않습니다."),
    NOT_EXISTS_MEMBER(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다."),
    NOT_EXISTS_THEME(HttpStatus.NOT_FOUND, "해당 테마가 존재하지 않습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 유효하지 않습니다."),
    NOT_ADMIN(HttpStatus.UNAUTHORIZED, "관리자 권한이 없습니다."),
    NOT_USER_OWN_RESERVATION(HttpStatus.FORBIDDEN, "사용자의 예약이 아닙니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
