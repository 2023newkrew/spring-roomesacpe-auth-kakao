package nextstep.reservation;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.annotation.AuthenticationPrincipal;
import nextstep.member.LoginMember;
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

    @PostMapping
    public ResponseEntity createReservation(@AuthenticationPrincipal LoginMember loginMember,
                                            @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(reservationRequest, loginMember.getId());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long id) {
        reservationService.deleteById(id, loginMember.getId());

        return ResponseEntity.noContent().build();
    }
}
