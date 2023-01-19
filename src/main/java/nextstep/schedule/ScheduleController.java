package nextstep.schedule;

import lombok.AllArgsConstructor;
import nextstep.schedule.dto.ScheduleRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/schedules")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        Long id = scheduleService.create(scheduleRequestDto);
        return ResponseEntity.created(URI.create("/schedules/" + id))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> showReservations(@RequestParam Long themeId, @RequestParam String date) {
        return ResponseEntity.ok()
                .body(scheduleService.findByThemeIdAndDate(themeId, date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent()
                .build();
    }
}
