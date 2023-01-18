package nextstep.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ReservationRequestDto;
import nextstep.support.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    public final ReservationService reservationService;

    @PostMapping
    public ResponseEntity createReservation(@RequestBody ReservationRequestDto reservationRequestDto, @AuthenticationPrincipal String username) {
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
    public ResponseEntity deleteReservation(@PathVariable Long id, @AuthenticationPrincipal String username) {
        reservationService.deleteById(id, username);

        return ResponseEntity.noContent()
            .build();
    }
}
