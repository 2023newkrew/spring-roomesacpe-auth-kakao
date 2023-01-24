package nextstep.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String AUTHORIZE_ERROR_TITLE = "인증 오류!";
    private static final String API_ERROR_TITLE = "API 오류!";

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException exception) {
        return handleExceptionInternal(API_ERROR_TITLE, exception.getErrorCode());
    }

    @ExceptionHandler(AuthorizeException.class)
    public ResponseEntity<?> handleAuthorizeException(AuthorizeException exception) {
        return handleExceptionInternal(AUTHORIZE_ERROR_TITLE, exception.getErrorCode());
    }

    private ResponseEntity<Object> handleExceptionInternal(String messageTitle, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(messageTitle, errorCode.getErrorCode(), errorCode.getMessage()));
    }
}
