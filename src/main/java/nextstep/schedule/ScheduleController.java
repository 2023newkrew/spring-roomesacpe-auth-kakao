package nextstep.schedule;

import nextstep.auth.NeedAuth;
import nextstep.member.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @NeedAuth(role = Role.ADMIN)
    @PostMapping
    public ResponseEntity createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(scheduleRequest);
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> showSchedules(@RequestParam Long themeId, @RequestParam String date) {
        return ResponseEntity.ok().body(scheduleService.findByThemeIdAndDate(themeId, date));
    }

    @NeedAuth(role = Role.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
