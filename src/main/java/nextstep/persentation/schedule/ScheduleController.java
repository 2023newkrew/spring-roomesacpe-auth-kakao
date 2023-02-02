package nextstep.persentation.schedule;

import nextstep.domain.schedule.ScheduleService;
import nextstep.dto.schedule.ScheduleRequest;
import nextstep.persistence.schedule.Schedule;
import nextstep.support.NotExistEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/admin/schedules")
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        if (scheduleRequest.isNotValid()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Long id = scheduleService.create(scheduleRequest);
            return ResponseEntity.created(URI.create("/admin/schedules/" + id)).build();
        }
        catch(NotExistEntityException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> showReservations(@RequestParam Long themeId, @RequestParam String date) {
        return ResponseEntity.ok().body(scheduleService.findByThemeIdAndDate(themeId, date));
    }

    @DeleteMapping("/admin/schedules/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        int deleteCount = scheduleService.deleteById(id);
        if (deleteCount == 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }
}
