package nextstep.exception;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_PARAMS(500, "InvalidParams", "필수 데이터 누락");

    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ErrorCode valueOfCode(String errorCode) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(errorCode))
                .findAny()
                .orElse(null);
    }
}
