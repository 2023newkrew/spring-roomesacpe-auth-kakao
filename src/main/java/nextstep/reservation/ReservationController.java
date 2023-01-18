package nextstep.reservation;

import nextstep.auth.MemberId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@MemberId Long memberId, @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(memberId, reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@MemberId Long memberId, @PathVariable Long id) {
        reservationService.deleteById(memberId, id);

        return ResponseEntity.noContent().build();
    }
}
