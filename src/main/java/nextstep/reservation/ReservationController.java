package nextstep.reservation;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.common.AuthenticationPrincipal;
import nextstep.reservation.dto.ReservationRequestDto;
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
    public ResponseEntity createReservation(
        @RequestBody ReservationRequestDto reservationRequestDto,
        @AuthenticationPrincipal String username) {
        Long id = reservationService.create(reservationRequestDto, username);
        return ResponseEntity.created(URI.create("/reservations/" + id))
            .build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok()
            .body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id,
        @AuthenticationPrincipal String username) {
        reservationService.deleteById(id, username);

        return ResponseEntity.noContent()
            .build();
    }
}
