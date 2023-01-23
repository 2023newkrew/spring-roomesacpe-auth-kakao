package nextstep.schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(scheduleRequest);
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> showReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Schedule> schedules = scheduleService.findByThemeIdAndDate(themeId, date);
        List<ScheduleResponse> scheduleResponses = schedules.stream().map(ScheduleResponse::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok().body(scheduleResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
