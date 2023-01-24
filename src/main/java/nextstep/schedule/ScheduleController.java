package nextstep.schedule;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(scheduleRequest);
        ScheduleResponse res = new ScheduleResponse(id, scheduleRequest.getThemeId(), scheduleRequest.getDate(), scheduleRequest.getTime());
        return ResponseEntity.created(URI.create("/schedules/").resolve(id.toString())).body(res);
    }

    @GetMapping
    public List<ScheduleResponse> showSchedules(@RequestParam Long themeId, @RequestParam String date) {
        List<Schedule> schedules = scheduleService.findByThemeIdAndDate(themeId, date);
        List<ScheduleResponse> res = schedules.stream()
                .map(s -> new ScheduleResponse(s.getId(), s.getTheme().getId(), s.getDate().toString(), s.getTime().toString()))
                .collect(Collectors.toList());
        return res;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelSchedule(@PathVariable Long id) {
        scheduleService.cancelById(id);
    }
}
