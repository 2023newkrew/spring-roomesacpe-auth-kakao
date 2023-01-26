package nextstep.reservation;

import nextstep.auth.AuthenticationPrincipal;
import nextstep.auth.NeedLogin;
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

    @NeedLogin
    @PostMapping
    public ResponseEntity createReservation(@AuthenticationPrincipal String username,
                                            @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(username, reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @NeedLogin
    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@AuthenticationPrincipal String username,
                                            @PathVariable Long id) {
        reservationService.deleteById(username, id);

        return ResponseEntity.noContent().build();
    }

}
