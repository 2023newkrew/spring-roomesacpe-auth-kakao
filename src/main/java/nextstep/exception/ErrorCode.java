package nextstep.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인 정보가 유효하지 않습니다."),
    DUPLICATED_ENTITY(HttpStatus.CONFLICT, "해당 자원이 이미 존재합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NO_SUCH_ENTITY(HttpStatus.NOT_FOUND, "해당 자원이 존재하지 않습니다."),
    INVALID_SCHEDULE_ID(HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 스케줄이 존재합니다."),
    INVALID_THEME_ID(HttpStatus.BAD_REQUEST, "요청에 유효하지 않는 테마가 존재합니다."),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "유효하지 않은 멤버 역할입니다."),
    ;

    private final HttpStatus status;

    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
