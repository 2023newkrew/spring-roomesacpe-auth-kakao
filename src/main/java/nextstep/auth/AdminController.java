package nextstep.auth;


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
    private final ScheduleService scheduleService;
    private final ThemeService themeService;

    public AdminController(ScheduleService scheduleService, ThemeService themeService) {
        this.scheduleService = scheduleService;
        this.themeService = themeService;
    }

    @PostMapping("/schedules")
    public ResponseEntity<String> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(scheduleRequest);
        return ResponseEntity.created(URI.create("/schedules/" + id)).body("Location: /schedules/" + id);
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/themes")
    public ResponseEntity<String> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).body("Location: /themes/" + id);
    }

    @DeleteMapping("themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
