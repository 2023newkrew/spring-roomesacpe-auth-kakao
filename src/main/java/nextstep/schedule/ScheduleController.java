package nextstep.schedule;

import nextstep.schedule.dto.ScheduleRequest;
import nextstep.theme.Theme;
import nextstep.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ThemeService themeService;

    public ScheduleController(ScheduleService scheduleService, ThemeService themeService) {
        this.scheduleService = scheduleService;
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Theme theme = themeService.findById(scheduleRequest.getThemeId());
        Long id = scheduleService.create(scheduleRequest, theme);
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> showReservations(@RequestParam Long themeId, @RequestParam String date) {
        return ResponseEntity.ok().body(scheduleService.findByThemeIdAndDate(themeId, date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
