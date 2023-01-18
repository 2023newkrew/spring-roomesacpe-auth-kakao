package nextstep.reservation;

import nextstep.support.ForbiddenException;
import nextstep.support.TokenExpirationException;
import nextstep.support.UnauthorizedException;
import nextstep.ui.AuthenticationPrincipal;
import nextstep.login.LoginMember;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Void> createReservation(@RequestBody Long scheduleId, @AuthenticationPrincipal LoginMember loginMember) {
        Long id = reservationService.create(scheduleId, loginMember);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<ReservationResponse> reservations = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id, @AuthenticationPrincipal LoginMember loginMember) {
        reservationService.deleteById(id, loginMember);
        return ResponseEntity.noContent().build();
    }

}
