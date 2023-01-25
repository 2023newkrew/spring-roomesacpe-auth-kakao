package nextstep.reservation;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.exception.ForbiddenAccessException;
import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.ui.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    public final ReservationService reservationService;
    public final MemberService memberService;

    @PostMapping
    public ResponseEntity<URI> createReservation(@AuthenticationPrincipal String token,
                                                 @RequestBody ReservationRequest reservationRequest) {
        memberService.findByToken(token);
        Long id = reservationService.create(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@AuthenticationPrincipal String token, @PathVariable Long id) {
        Member member = memberService.findByToken(token);
        Reservation reservation = reservationService.findById(id);
        if (!reservation.getName().equals(member.getUsername())) {
            throw new ForbiddenAccessException();
        }
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
