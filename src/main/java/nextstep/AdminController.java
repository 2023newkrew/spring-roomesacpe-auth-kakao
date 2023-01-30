package nextstep;

import nextstep.config.auth.Auth;
import nextstep.schedule.ScheduleRequest;
import nextstep.schedule.ScheduleService;
import nextstep.theme.ThemeRequest;
import nextstep.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ThemeService themeService;
    private final ScheduleService scheduleService;

    public AdminController(ThemeService themeService, ScheduleService scheduleService) {
        this.themeService = themeService;
        this.scheduleService = scheduleService;
    }


    @Auth(role = Auth.Role.ADMIN)
    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @Auth(role = Auth.Role.ADMIN)
    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.delete(id);

        return ResponseEntity.noContent().build();
    }


    @Auth(role = Auth.Role.ADMIN)
    @PostMapping("/schedules")
    public ResponseEntity createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(scheduleRequest);
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @Auth(role = Auth.Role.ADMIN)
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
