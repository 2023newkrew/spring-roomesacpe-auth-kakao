package nextstep.reservation;

import nextstep.auth.domain.LoginMember;
import nextstep.auth.principal.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<Void> createReservation(@AuthenticationPrincipal LoginMember member,
                                                  @Validated @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(member.getName(), reservationRequest);
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
    public ResponseEntity<Void> deleteReservation(@AuthenticationPrincipal LoginMember member,
                                                  @PathVariable Long id) {
        reservationService.deleteById(member.getName(), id);

        return ResponseEntity.noContent()
                .build();
    }
}
