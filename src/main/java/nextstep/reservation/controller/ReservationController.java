package nextstep.reservation.controller;

import lombok.RequiredArgsConstructor;
import nextstep.auth.support.AuthPrincipal;
import nextstep.auth.support.LoginRequired;
import nextstep.member.model.Member;
import nextstep.reservation.model.Reservation;
import nextstep.reservation.model.ReservationRequest;
import nextstep.reservation.service.ReservationService;
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
    public ResponseEntity<Void> createReservation(@AuthPrincipal Member member, @Valid @RequestBody ReservationRequest reservationRequest) {
        reservationRequest.setUsername(member.getName());
        Long id = reservationService.create(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @LoginRequired
    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@AuthPrincipal Member member, @RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date, member);
        return ResponseEntity.ok().body(results);
    }

    @LoginRequired
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@AuthPrincipal Member member, @PathVariable Long id) {
        reservationService.deleteById(id, member);

        return ResponseEntity.noContent().build();
    }
}
