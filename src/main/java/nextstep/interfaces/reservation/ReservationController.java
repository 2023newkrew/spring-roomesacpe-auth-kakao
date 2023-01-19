package nextstep.interfaces.reservation;

import nextstep.infrastructure.web.UserContextHolder;
import nextstep.infrastructure.web.AuthenticationPrincipal;
import nextstep.domain.reservation.Reservation;
import nextstep.interfaces.reservation.dto.ReservationRequest;
import nextstep.domain.reservation.ReservationService;
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
    public ResponseEntity createReservation(@RequestBody ReservationRequest reservationRequest, @AuthenticationPrincipal UserContextHolder userContextHolder) {
        Long id = reservationService.create(reservationRequest, userContextHolder.getId());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id, @AuthenticationPrincipal UserContextHolder userContextHolder) {
        reservationService.deleteById(id, userContextHolder.getId());

        return ResponseEntity.noContent().build();
    }

}
