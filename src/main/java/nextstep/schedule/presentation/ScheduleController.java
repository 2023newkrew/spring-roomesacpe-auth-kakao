package nextstep.schedule.presentation;

import nextstep.schedule.domain.Schedule;
import nextstep.schedule.domain.ScheduleService;
import nextstep.schedule.dto.ScheduleRequest;
import nextstep.support.NotExistEntityException;
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

    @PostMapping
    public ResponseEntity<Object> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        if (scheduleRequest.isNotValid()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Long id = scheduleService.create(scheduleRequest);
            return ResponseEntity.created(URI.create("/schedules/" + id)).build();
        }
        catch(NotExistEntityException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> showReservations(@RequestParam Long themeId, @RequestParam String date) {
        return ResponseEntity.ok().body(scheduleService.findByThemeIdAndDate(themeId, date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        int deleteCount = scheduleService.deleteById(id);
        if (deleteCount == 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }
}
