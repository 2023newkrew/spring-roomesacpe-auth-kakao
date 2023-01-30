package nextstep.reservation;

import nextstep.auth.annotation.Authenticated;
import nextstep.auth.LoginUser;
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
    public ResponseEntity<Object> createReservation(@RequestBody ReservationRequest reservationRequest, @Authenticated LoginUser loginUser) {
        Long id = reservationService.create(reservationRequest, loginUser.getId());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id, @Authenticated LoginUser loginUser) {
        reservationService.deleteById(id, loginUser.getId());

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
