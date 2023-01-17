package nextstep.reservation;

import nextstep.member.Member;
import nextstep.support.*;
import nextstep.support.excpetion.DuplicateReservationException;
import nextstep.support.excpetion.InvalidAuthorizationTokenException;
import nextstep.support.excpetion.NotExistReservationException;
import nextstep.support.excpetion.NotQualifiedMemberException;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler({NotQualifiedMemberException.class, InvalidAuthorizationTokenException.class})
    public ResponseEntity handleUnauthorizedException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler()
    public ResponseEntity handleNotFoundException(NotExistReservationException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler()
    public ResponseEntity handleBadRequestException(DuplicateReservationException ex) {
        return ResponseEntity.badRequest().build();
    }
}
