package nextstep.reservation;

import nextstep.auth.LoginMember;
import nextstep.config.AuthenticationPrincipal;
import nextstep.member.MemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class ReservationController {

    public final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 기존 소스에서 @AuthenticationPrincipal annotation을 통해 요청에 대한 검증을 포함
    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequest reservationRequest, @AuthenticationPrincipal LoginMember member) {
        Long id = reservationService.create(reservationRequest, member.getMemberId());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @PostMapping("/admin/reservations")
    public ResponseEntity<Void> createReservationAdmin(@RequestBody ReservationAdminRequest reservationAdminRequest) {
        Long id = reservationService.create(new ReservationRequest(reservationAdminRequest.getScheduleId()),
                reservationAdminRequest.getMemberId());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    // 기존 소스에서 @AuthenticationPrincipal annotation을 통해 요청에 대한 검증을 포함
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id, @AuthenticationPrincipal LoginMember member) {
        reservationService.deleteById(id, member.getMemberId());
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/admin/reservations/{id}")
    public ResponseEntity<Void> deleteReservationAdmin(@PathVariable Long id) {
        reservationService.deleteByIdAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
