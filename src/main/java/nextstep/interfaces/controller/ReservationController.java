package nextstep.interfaces.controller;

import nextstep.domain.model.template.annotation.Login;
import nextstep.domain.domain.Reservation;
import nextstep.domain.model.request.ReservationRequest;
import nextstep.service.ReservationService;
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
    public ResponseEntity createReservation(@RequestBody ReservationRequest reservationRequest,
                                            @Login Long memberId) {

        Long id = reservationService.create(reservationRequest, memberId);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId,
                                           @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id,
                                            @Login Long memberId) {
        reservationService.deleteById(id, memberId);

        return ResponseEntity.noContent().build();
    }

}
