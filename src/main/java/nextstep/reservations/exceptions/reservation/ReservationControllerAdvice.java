package nextstep.reservations.exceptions.reservation;

import nextstep.reservations.controller.reservation.ReservationController;
import nextstep.reservations.exceptions.reservation.exception.DuplicateReservationException;
import nextstep.reservations.exceptions.reservation.exception.NoSuchReservationException;
import nextstep.reservations.exceptions.reservation.exception.NotAvailableTimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = ReservationController.class)
public class ReservationControllerAdvice {
    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<String> duplicateReservation() {
        return ResponseEntity
                .badRequest()
                .body("해당 시간에 해당 테마의 중복된 예약이 있습니다.");
    }
    
    @ExceptionHandler(NotAvailableTimeException.class)
    public ResponseEntity<String> notAvailableTime() {
        return ResponseEntity
                .badRequest()
                .body("예약할 수 없는 시간입니다.");
    }

    @ExceptionHandler(NoSuchReservationException.class)
    public ResponseEntity<String> noSuchReservation() {
        return ResponseEntity
                .badRequest()
                .body("존재하지 않는 예약입니다.");
    }
}
