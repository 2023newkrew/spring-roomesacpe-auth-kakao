package nextstep.admin;

import nextstep.auth.AuthService;
import nextstep.auth.LoginMember;
import nextstep.auth.TokenResponse;
import nextstep.config.auth.LoginUser;
import nextstep.reservation.ReservationService;
import nextstep.schedule.ScheduleService;
import nextstep.theme.ThemeRequest;
import nextstep.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;
    private final ReservationService reservationService;
    private final ThemeService themeService;
    private final ScheduleService scheduleService;

    public AdminController(AuthService authService, ReservationService reservationService, ThemeService themeService, ScheduleService scheduleService) {
        this.authService = authService;
        this.reservationService = reservationService;
        this.themeService = themeService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/token")
    public ResponseEntity<TokenResponse> getAdminToken() {
        TokenResponse tokenResponse = authService.createAdminToken();
        return ResponseEntity.ok(tokenResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity deleteReservation(@LoginUser LoginMember loginMember, @PathVariable Long id) {

        reservationService.deleteById(id, loginMember);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
