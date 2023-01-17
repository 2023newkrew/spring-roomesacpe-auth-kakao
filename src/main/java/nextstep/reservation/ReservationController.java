package nextstep.reservation;

import nextstep.auth.AuthPrincipal;
import nextstep.member.Member;
import nextstep.support.DuplicateEntityException;
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
    public ResponseEntity createReservation(@AuthPrincipal Member member, @RequestBody ReservationRequest reservationRequest) {
        reservationRequest.setName(member.getName());
        Long id = reservationService.create(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({NullPointerException.class, DuplicateEntityException.class})
    public ResponseEntity onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
