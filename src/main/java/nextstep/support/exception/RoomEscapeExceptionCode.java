package nextstep.support.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
public enum RoomEscapeExceptionCode {
    AUTHENTICATION_FAIL(UNAUTHORIZED, "계정을 인증할 수 없습니다."),
    NOT_FOUND_MEMBER(NOT_FOUND, "멤버를 찾을 수 없습니다."),
    NOT_FOUND_RESERVATION(NOT_FOUND, "예약을 찾을 수 없습니다."),
    NOT_OWN_RESERVATION(FORBIDDEN, "본인의 예약이 아닙니다."),
    NOT_FOUND_THEME(NOT_FOUND, "테마를 찾을 수 없습니다."),
    NOT_FOUND_SCHEDULE(NOT_FOUND, "스케줄을 찾을 수 없습니다."),
    SCHEDULE_ALREADY_RESERVED(FORBIDDEN, "해당 스케줄에 예약이 이미 찼습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "토큰 검증에 실패했습니다."),
    FORBIDDEN_ACCESS(FORBIDDEN, "관리자만 접속할 수 있습니다.");
    private final HttpStatus httpStatus;
    private final String errorMessage;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
