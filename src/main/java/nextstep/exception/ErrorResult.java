package nextstep.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResult {
    private final int status;
    private final String code;
    private final String message;
}
