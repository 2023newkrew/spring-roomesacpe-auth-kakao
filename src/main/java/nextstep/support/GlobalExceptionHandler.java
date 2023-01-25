package nextstep.support;

import io.jsonwebtoken.JwtException;
import nextstep.support.error.ErrorCode;
import nextstep.support.error.ErrorResponse;
import nextstep.support.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateThemeException.class)
    public ResponseEntity<ErrorResponse> duplicateThemeExceptionHandler() {
        return ErrorResponse.toResponseEntity(ErrorCode.DUPLICATE_THEME);
    }

    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<ErrorResponse> duplicateReservationExceptionHandler() {
        return ErrorResponse.toResponseEntity(ErrorCode.DUPLICATE_RESERVATION);
    }

    @ExceptionHandler({JwtException.class, InvalidAccessTokenException.class})
    public ResponseEntity<ErrorResponse> accessTokenExceptionHandler() {
        return ErrorResponse.toResponseEntity(ErrorCode.INVALID_ACCESS_TOKEN);
    }

    @ExceptionHandler(NotAdminException.class)
    public ResponseEntity<ErrorResponse> notAdminExceptionHandler() {
        return ErrorResponse.toResponseEntity(ErrorCode.NOT_ADMIN);
    }

    @ExceptionHandler(NotExistReservationException.class)
    public ResponseEntity<ErrorResponse> notExistReservationExceptionHandler() {
        return ErrorResponse.toResponseEntity(ErrorCode.NOT_EXISTS_RESERVATION);
    }

    @ExceptionHandler(NotExistMemberException.class)
    public ResponseEntity<ErrorResponse> notExistMemberExceptionHandler() {
        return ErrorResponse.toResponseEntity(ErrorCode.NOT_EXISTS_RESERVATION);
    }

    @ExceptionHandler(NotExistThemeException.class)
    public ResponseEntity<ErrorResponse> notExistThemeExceptionHandler() {
        return ErrorResponse.toResponseEntity(ErrorCode.NOT_EXISTS_THEME);
    }

    @ExceptionHandler(NotUserOwnReservationException.class)
    public ResponseEntity<ErrorResponse> ForbiddenExceptionHandler(Exception exception) {
        return ErrorResponse.toResponseEntity(ErrorCode.NOT_USER_OWN_RESERVATION);
    }
}
