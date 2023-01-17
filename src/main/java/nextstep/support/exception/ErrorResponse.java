package nextstep.support.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String title;
    private int errorCode;
    private String message;
}
