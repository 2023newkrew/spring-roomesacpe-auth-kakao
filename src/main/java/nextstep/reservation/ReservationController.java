package nextstep.reservation;

import nextstep.auth.AuthService;
import nextstep.auth.Login;
import nextstep.member.LoginMember;
import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.support.UnauthorizedAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthService authService;
    private final MemberService memberService;

    public ReservationController(ReservationService reservationService, AuthService authService, MemberService memberService) {
        this.reservationService = reservationService;
        this.authService = authService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@Login LoginMember loginMember, @RequestBody ReservationRequest reservationRequest) {
        authService.validateLoginMember(loginMember);
        Member member = memberService.findById(loginMember.getId());
        Long id = reservationService.create(reservationRequest, member);

        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@Login LoginMember loginMember, @PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
