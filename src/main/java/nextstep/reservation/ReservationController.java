package nextstep.reservation;

import nextstep.member.Member;
import nextstep.support.*;
import nextstep.support.excpetion.DuplicateReservationException;
import nextstep.support.excpetion.InvalidAuthorizationTokenException;
import nextstep.support.excpetion.NotExistReservationException;
import nextstep.support.excpetion.NotQualifiedMemberException;
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
    public ResponseEntity createReservation(@RequestBody ReservationRequest reservationRequest, @LoginMember Member member) {
        Long id = reservationService.create(reservationRequest, member);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id, @LoginMember Member member) {
        reservationService.deleteById(id, member);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({NotExistReservationException.class, NotQualifiedMemberException.class, DuplicateReservationException.class, NotQualifiedMemberException.class, InvalidAuthorizationTokenException.class})
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().build();
    }
}
