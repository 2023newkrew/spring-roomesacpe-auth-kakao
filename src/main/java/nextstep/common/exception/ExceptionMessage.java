package nextstep.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    ACCESSTOKEN_IS_NULL("액세스 토큰이 존재하지 않습니다."),
    DUPLICATED_USERNAME("같은 username을 가진 회원이 존재합니다."),
    NOT_EXIST_MEMBER("존재하지 않는 회원입니다."),
    INVALID_RESERVATION_ID("예약번호가 유효하지 않습니다."),
    UNAUTHORIZED_RESERVATION("자신의 예약이 아닙니다."),
    INVALID_SCHEDULE_ID("스케줄번호가 유효하지 않습니다."),
    INVALID_THEME_ID("테마번호가 유효하지 않습니다."),
    WRONG_PASSWORD("잘못된 비밀번호입니다."),
    ADMIN_ONLY("관리자만 접근가능합니다."),

    ;
    private String message;
}
