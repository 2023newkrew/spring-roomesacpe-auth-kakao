package nextstep.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.auth.AuthPrincipal;
import nextstep.auth.LoginRequired;
import nextstep.member.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    public final ReservationService reservationService;

    @LoginRequired
    @PostMapping
    public ResponseEntity createReservation(@AuthPrincipal Member member, @Valid @RequestBody ReservationRequest reservationRequest) {
        reservationRequest.setUsername(member.getName());
        Long id = reservationService.create(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @LoginRequired
    @GetMapping
    public ResponseEntity readReservations(@AuthPrincipal Member member, @RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date, member);
        return ResponseEntity.ok().body(results);
    }

    @LoginRequired
    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@AuthPrincipal Member member, @PathVariable Long id) {
        reservationService.deleteById(id, member);

        return ResponseEntity.noContent().build();
    }
}
