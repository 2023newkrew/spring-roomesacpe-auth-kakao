package nextstep.schedule;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        Long id = scheduleService.create(scheduleRequest);
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    /**
     * If "/admin"  path is not attached, redirect to "/admin/**"
     */
    @PostMapping("/schedules")
    public ResponseEntity<Void> createScheduleWithoutAdmin(@RequestBody ScheduleRequest scheduleRequest){
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/admin/schedules")).build();
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> showSchedules(@RequestParam Long themeId, @RequestParam String date) {
        return ResponseEntity.ok().body(scheduleService.findByThemeIdAndDate(themeId, date));
    }

    @DeleteMapping("/admin/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * If "/admin"  path is not attached, redirect to "/admin/**"
     */
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteScheduleWithoutAdmin(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/admin/schedules/"+id)).build();
    }
}
