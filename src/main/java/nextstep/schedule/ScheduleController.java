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

    @GetMapping
    public ResponseEntity<List<Schedule>> showReservations(@RequestParam Long themeId, @RequestParam String date) {
        return ResponseEntity.ok()
                .body(scheduleService.findByThemeIdAndDate(themeId, date));
    }

}
