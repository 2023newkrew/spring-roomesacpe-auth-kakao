package nextstep.schedule.controller;

import nextstep.schedule.dto.ScheduleRequest;
import nextstep.schedule.entity.Schedule;
import nextstep.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(scheduleRequest);
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> showReservations(@RequestParam Long themeId, @RequestParam String date) {
        return ResponseEntity.ok().body(scheduleService.findByThemeIdAndDate(themeId, date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
