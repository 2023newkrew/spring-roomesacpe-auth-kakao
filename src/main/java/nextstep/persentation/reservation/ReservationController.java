package nextstep.persentation.reservation;

import nextstep.annotation.AuthenticationPrincipal;
import nextstep.domain.reservation.ReservationService;
import nextstep.dto.dto.ReservationRequest;
import nextstep.persistence.member.LoginMember;
import nextstep.persistence.reservation.Reservation;
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
    public ResponseEntity<Void> createReservation(@AuthenticationPrincipal LoginMember loginMember,
                                                     @RequestBody ReservationRequest reservationRequest) {
        if (reservationRequest.isNotValid()) {
            return ResponseEntity.badRequest().build();
        }
        Long id = reservationService.create(reservationRequest, loginMember.getId());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long id) {
        reservationService.deleteById(id, loginMember.getId());

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
