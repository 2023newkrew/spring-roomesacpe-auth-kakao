package nextstep.support.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String title;
    private int errorCode;
    private String message;
}
