package nextstep.reservation;

import nextstep.auth.AuthorizationExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    /**
     * public ResponseEntity me(HttpServletRequest request) {
     *         String token = AuthorizationExtractor.extract(request);
     *         Optional<Member> member = memberService.findMemberByToken(token);
     *         if (member.isEmpty()) {
     *             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("토큰 안됨");
     *         }
     *         return ResponseEntity.ok().body(member.get());
     *     }
     */
    @PostMapping
    public ResponseEntity createReservation(@RequestBody ReservationRequest reservationRequest, HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        Long id = reservationService.create(reservationRequest, token);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity onException(Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
