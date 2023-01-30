package nextstep.reservation;

import nextstep.auth.LoginMember;
import nextstep.config.auth.LoginUser;
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
    public ResponseEntity createReservation(@LoginUser LoginMember loginMember, @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(reservationRequest, loginMember);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@LoginUser LoginMember loginMember, @PathVariable Long id) {

        reservationService.deleteById(id, loginMember);
        return ResponseEntity.noContent().build();
    }
}
