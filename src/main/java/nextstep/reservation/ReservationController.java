package nextstep.reservation;

import nextstep.member.Member;
import nextstep.support.annotation.AuthorizationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequest reservationRequest, @AuthorizationPrincipal Member member) {
        Long id = reservationService.create(reservationRequest, member);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> reservations = reservationService.findAllByThemeIdAndDate(themeId, date);
        List<ReservationResponse> reservationResponses = reservations.stream().map(ReservationResponse::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok().body(reservationResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id, @AuthorizationPrincipal Member member) {
        reservationService.deleteById(id, member);

        return ResponseEntity.noContent().build();
    }

}
