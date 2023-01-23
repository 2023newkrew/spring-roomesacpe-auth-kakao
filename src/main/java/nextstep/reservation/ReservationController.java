package nextstep.reservation;

import nextstep.auth.AuthenticationPrincipal;
import nextstep.member.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@AuthenticationPrincipal Member member, @RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.reserve(member, reservationRequest);
        ReservationResponse res = new ReservationResponse(reservation.getId(), reservation.getSchedule(), reservation.getName());
        return ResponseEntity.created(URI.create("/reservations/").resolve(reservation.getId().toString())).body(res);
    }

    @GetMapping
    public List<ReservationResponse> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> reservations = reservationService.findAllByThemeIdAndDate(themeId, date);
        List<ReservationResponse> res = reservations.stream()
                .map(r -> new ReservationResponse(r.getId(), r.getSchedule(), r.getName()))
                .collect(Collectors.toList());
        return res;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        reservationService.cancelReservation(id, member);
    }
}
