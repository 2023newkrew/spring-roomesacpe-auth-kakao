package nextstep.reservation.controller;

import nextstep.auth.service.AuthService;
import nextstep.auth.argumentresolver.Login;
import nextstep.member.dto.LoginMember;
import nextstep.member.Member;
import nextstep.member.service.MemberService;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<ReservationResponse>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<ReservationResponse> results = reservationService.findAllByThemeIdAndDate(themeId, date)
                .stream()
                .map(ReservationResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@Login LoginMember loginMember, @PathVariable Long id) {
        authService.validateLoginMember(loginMember);
        reservationService.deleteById(id, loginMember.getId());
        return ResponseEntity.noContent().build();
    }
}
