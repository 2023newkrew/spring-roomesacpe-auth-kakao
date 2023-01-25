package nextstep.reservation;

import java.util.stream.Collectors;
import nextstep.auth.AuthenticationPrincipal;
import nextstep.exception.DuplicateEntityException;
import nextstep.exception.NotExistEntityException;
import nextstep.exception.UnauthorizedException;
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
    public ResponseEntity createReservation(@AuthenticationPrincipal Long memberId, @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(memberId, reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> reservations = reservationService.findAllByThemeIdAndDate(themeId, date);
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@AuthenticationPrincipal Long memberId, @PathVariable Long id) {
        reservationService.deleteById(memberId, id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({NotExistEntityException.class, DuplicateEntityException.class, UnauthorizedException.class})
    public ResponseEntity onBadRequest(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity onInternalServerError() {
        return ResponseEntity.internalServerError().body("요청을 처리할 수 없습니다.");
    }
}
