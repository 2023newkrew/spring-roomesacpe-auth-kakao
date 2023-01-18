package nextstep.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class RoomEscapeException extends RuntimeException{
    private final ErrorCode errorCode;

    public String getMessage(){
        return errorCode.getMessage();
    }

    public HttpStatus getHttpStatus(){
        return errorCode.getHttpStatus();
    }
}
