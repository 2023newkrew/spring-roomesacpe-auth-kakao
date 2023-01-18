package nextstep.reservation;

import nextstep.member.Member;
import nextstep.support.annotation.AuthorizationPrincipal;
import nextstep.support.exception.AuthorizationExcpetion;
import nextstep.support.exception.DuplicateReservationException;
import nextstep.support.exception.NotExistThemeException;
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
    public ResponseEntity createReservation(@RequestBody ReservationRequest reservationRequest, @AuthorizationPrincipal Member member) {
        Long id = reservationService.create(reservationRequest, member);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id, @AuthorizationPrincipal Member member) {
        reservationService.deleteById(id, member);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(value = {AuthorizationExcpetion.class, DuplicateReservationException.class, NotExistThemeException.class})
    public ResponseEntity onException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
