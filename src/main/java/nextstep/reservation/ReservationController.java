package nextstep.reservation;

import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.ui.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public final ReservationService reservationService;
    public final MemberService memberService;

    public ReservationController(ReservationService reservationService, MemberService memberService) {
        this.reservationService = reservationService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity createReservation(@AuthenticationPrincipal String token, @RequestBody ReservationRequest reservationRequest) {
        // 요청에 token이 포함되지 않았다면 401 리턴
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // 요청에 포함된 토큰으로 멤버 조회 --> 없으면 NullPointerException 던짐
        memberService.findByToken(token);

        Long id = reservationService.create(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@AuthenticationPrincipal String token, @PathVariable Long id) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 권한 체크(본인의 예약건인지 확인)
        Member member = memberService.findByToken(token);
        Reservation reservation = reservationService.findById(id);
        // 본인의 예약건이 아니라면 403 리턴
        if (!reservation.getName().equals(member.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // 본인의 예약건이라면 삭제
        reservationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
