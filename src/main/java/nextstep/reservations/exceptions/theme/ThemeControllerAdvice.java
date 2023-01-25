package nextstep.reservations.exceptions.theme;

import nextstep.reservations.controller.reservation.ReservationController;
import nextstep.reservations.controller.theme.ThemeController;
import nextstep.reservations.exceptions.theme.exception.DuplicateThemeException;
import nextstep.reservations.exceptions.theme.exception.NoSuchThemeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {ThemeController.class, ReservationController.class})
public class ThemeControllerAdvice {
    @ExceptionHandler(DuplicateThemeException.class)
    public ResponseEntity<String> duplicateTheme() {
        return ResponseEntity
                .badRequest()
                .body("이미 동일한 이름의 테마가 있습니다.");
    }

    @ExceptionHandler(NoSuchThemeException.class)
    public ResponseEntity<String> noSuchTheme() {
        return ResponseEntity
                .badRequest()
                .body("존재하지 않는 테마입니다.");
    }
}
