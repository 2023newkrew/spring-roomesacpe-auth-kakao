package nextstep.reservation;

import nextstep.auth.LoginMember;
import nextstep.config.AuthenticationPrincipal;
import nextstep.member.Member;
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
    public ResponseEntity createReservation(@RequestBody ReservationRequest reservationRequest, @AuthenticationPrincipal LoginMember loginMember) {
        Long id = reservationService.create(reservationRequest, loginMember);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id, @AuthenticationPrincipal LoginMember loginMember) {
        reservationService.deleteById(id, loginMember.getId());

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
