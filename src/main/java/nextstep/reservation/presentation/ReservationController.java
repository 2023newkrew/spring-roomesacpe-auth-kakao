package nextstep.reservation.presentation;

import nextstep.annotation.AuthenticationPrincipal;
import nextstep.member.domain.LoginMember;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.ReservationService;
import nextstep.reservation.dto.ReservationRequest;
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
    public ResponseEntity<Object> createReservation(@AuthenticationPrincipal LoginMember loginMember,
                                                     @RequestBody ReservationRequest reservationRequest) {
        if (!reservationRequest.validate()) {
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
    public ResponseEntity<Object> deleteReservation(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long id) {
        reservationService.deleteById(id, loginMember.getId());

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
