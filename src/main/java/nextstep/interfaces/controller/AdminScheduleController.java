package nextstep.interfaces.controller;

import lombok.RequiredArgsConstructor;
import nextstep.domain.model.request.ScheduleRequest;
import nextstep.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/schedules")
public class AdminScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(scheduleRequest);
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
