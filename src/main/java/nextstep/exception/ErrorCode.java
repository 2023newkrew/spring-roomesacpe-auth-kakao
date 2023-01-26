package nextstep.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NOT_LOGGED_IN(401, "Unauthorized", "로그인이 필요합니다."),
    FORBIDDEN_ACCESS(403, "Forbidden", "권한이 없습니다."),
    LOGIN_FAIL(400, "Bad Request", "로그인에 실패하였습니다."),
    DUPLICATED_ENTITY(400, "Bad Request", "이미 존재하여 생성할 수 없습니다."),
    NOT_EXIST_ENTITY(400, "Bad Request", "이미 존재하여 생성할 수 없습니다.");


    private final int status;
    private final String code;
    private final String message;
}
