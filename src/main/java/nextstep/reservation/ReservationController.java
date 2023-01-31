package nextstep.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;



@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody ReservationRequest reservationRequest,
                                            @RequestHeader(value = "Authorization") String authorization) {
        Long id = reservationService.create(reservationRequest, authorization);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body("Location: /reservations/" + id);
    }

    // localhost:8080/reservations?themeId=1&date=2022-02-01
    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(@RequestParam Long themeId, @RequestParam String date,
                                            @RequestHeader(value = "Authorization") String authorization) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date, authorization);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id,
                                            @RequestHeader(value = "Authorization") String authorization) {
        reservationService.deleteById(id, authorization);
        return ResponseEntity.noContent().build();
    }

}
