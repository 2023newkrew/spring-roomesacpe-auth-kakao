package nextstep.support;

import nextstep.support.error.ErrorResponse;
import nextstep.support.error.ErrorType;
import nextstep.support.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthorizationException.class)
    protected ResponseEntity<ErrorResponse> handleAuthorizationException(AuthorizationException e) {
        return ResponseEntity.status(ErrorType.UNAUTHORIZED.getStatusCode())
                .body(new ErrorResponse(ErrorType.UNAUTHORIZED.getMessage()));
    }

    @ExceptionHandler(DuplicateEntityException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicateEntityException(DuplicateEntityException e) {
        return ResponseEntity.status(ErrorType.DUPLICATE_ENTITY.getStatusCode())
                .body(new ErrorResponse(ErrorType.DUPLICATE_ENTITY.getMessage()));
    }

    @ExceptionHandler(InvalidMemberException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidMemberException(InvalidMemberException e) {
        return ResponseEntity.status(ErrorType.INVALID_MEMBER.getStatusCode())
                .body(new ErrorResponse(ErrorType.INVALID_MEMBER.getMessage()));
    }

    @ExceptionHandler(NotExistMemberException.class)
    protected ResponseEntity<ErrorResponse> NotExistMemberException(NotExistMemberException e) {
        return ResponseEntity.status(ErrorType.NOT_EXIST_MEMBER.getStatusCode())
                .body(new ErrorResponse(ErrorType.NOT_EXIST_MEMBER.getMessage()));
    }

    @ExceptionHandler(NotExistReservationException.class)
    protected ResponseEntity<ErrorResponse> NotExistReservationException(NotExistReservationException e) {
        return ResponseEntity.status(ErrorType.NOT_EXIST_RESERVATION.getStatusCode())
                .body(new ErrorResponse(ErrorType.NOT_EXIST_RESERVATION.getMessage()));
    }

    @ExceptionHandler(NotExistScheduleException.class)
    protected ResponseEntity<ErrorResponse> NotExistScheduleException(NotExistScheduleException e) {
        return ResponseEntity.status(ErrorType.NOT_EXIST_SCHEDULE.getStatusCode())
                .body(new ErrorResponse(ErrorType.NOT_EXIST_SCHEDULE.getMessage()));
    }

    @ExceptionHandler(NotExistThemeException.class)
    protected ResponseEntity<ErrorResponse> NotExistThemeException(NotExistThemeException e) {
        return ResponseEntity.status(ErrorType.NOT_EXIST_THEME.getStatusCode())
                .body(new ErrorResponse(ErrorType.NOT_EXIST_THEME.getMessage()));
    }



}
