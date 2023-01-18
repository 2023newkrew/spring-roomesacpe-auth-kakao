package nextstep.reservation.controller;

import nextstep.global.config.annotation.ExtractPrincipal;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity createReservation(@ExtractPrincipal String memberId, @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(reservationRequest.getScheduleId(), Long.parseLong(memberId));

        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);

        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@ExtractPrincipal String memberId, @PathVariable Long id) {
        reservationService.delete(id, Long.parseLong(memberId));

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
