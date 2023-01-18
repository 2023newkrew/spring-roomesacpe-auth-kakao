package nextstep.reservation;

import nextstep.auth.AuthenticationPrincipal;
import nextstep.exceptions.exception.ReservationForbiddenException;
import nextstep.member.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @AuthenticationPrincipal Member member,
            @RequestBody ReservationRequest reservationRequest
    ) {
        if (!reservationRequest.getName().equals(member.getUsername())) {
            throw new ReservationForbiddenException("예약자가 일치해야만 예약을 생성할 수 있습니다.");
        }
        Long id = reservationService.create(reservationRequest);
        ReservationResponse res = new ReservationResponse(id, reservationRequest.getScheduleId(), reservationRequest.getName());
        return ResponseEntity.created(URI.create("/reservations/").resolve(id.toString())).body(res);
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(
            @AuthenticationPrincipal Member member,
            @PathVariable Long id
    ) {
        Reservation reservation = reservationService.findById(id);
        if (!reservation.getName().equals(member.getUsername())) {
            throw new ReservationForbiddenException("예약당사자만 예약을 삭제할 수 있습니다.");
        }
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
