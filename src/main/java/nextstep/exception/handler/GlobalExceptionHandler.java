package nextstep.exception.handler;

import nextstep.exception.BusinessException;
import nextstep.exception.CommonErrorCode;
import nextstep.exception.ErrorCode;
import nextstep.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.getHttpStatus()
                        .value(), errorCode.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        ErrorCode errorCode = CommonErrorCode.SERVER_ERROR;
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.getHttpStatus()
                        .value(), errorCode.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println(e.getMessage());
        ErrorCode errorCode = CommonErrorCode.BAD_PARAMETER_REQUEST;

        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList())
                .get(0);

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.getHttpStatus()
                        .value(), message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageException(HttpMessageNotReadableException e) {
        System.out.println(e.getMessage());
        ErrorCode errorCode = CommonErrorCode.BAD_PARAMETER_REQUEST;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.getHttpStatus()
                        .value(), errorCode.getMessage()));
    }
}
