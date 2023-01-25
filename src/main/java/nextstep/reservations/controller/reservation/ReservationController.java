package nextstep.reservations.controller.reservation;

import nextstep.reservations.domain.service.reservation.ReservationService;
import nextstep.reservations.dto.reservation.ReservationRequestDto;
import nextstep.reservations.dto.reservation.ReservationResponseDto;
import nextstep.reservations.dto.reservation.TimeTable;
import nextstep.reservations.exceptions.reservation.exception.NotAvailableTimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Object> addReservation(@RequestBody ReservationRequestDto requestDto) {
        if (!TimeTable.values.contains(requestDto.getTime())) {
            throw new NotAvailableTimeException();
        }
        Long id = reservationService.addReservation(requestDto);
        return ResponseEntity
                .created(URI.create("/reservations/" + id))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservation(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(reservationService.getReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
