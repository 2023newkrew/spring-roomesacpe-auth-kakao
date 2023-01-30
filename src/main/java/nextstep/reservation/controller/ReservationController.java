package nextstep.reservation.controller;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import nextstep.annotation.AuthenticationPrincipal;
import nextstep.member.domain.LoginMember;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    public final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Void> createReservation(@AuthenticationPrincipal LoginMember loginMember,
                                                  @RequestBody @Valid ReservationRequest reservationRequest) {
        Long id = reservationService.create(reservationRequest, loginMember.getId());
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam @Min(1L) Long themeId,
                                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@AuthenticationPrincipal LoginMember loginMember,
                                                  @PathVariable @Min(1L) Long id) {
        reservationService.deleteById(id, loginMember.getId());

        return ResponseEntity.noContent().build();
    }
}
