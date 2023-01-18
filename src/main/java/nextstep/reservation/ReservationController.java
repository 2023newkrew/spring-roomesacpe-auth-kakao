package nextstep.reservation;

import nextstep.support.AuthenticationPrincipal;
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
    public ResponseEntity<URI> createReservation(@RequestBody ReservationRequest reservationRequest, @AuthenticationPrincipal String username) {
        Long id = reservationService.create(reservationRequest, username);
        return ResponseEntity.created(URI.create("/reservations/" + id))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok()
                .body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id, @AuthenticationPrincipal String username) {
        reservationService.deleteById(id, username);
        return ResponseEntity.noContent()
                .build();
    }
}
