package nextstep.schedule.controller;

import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import nextstep.schedule.dto.request.ScheduleRequest;
import nextstep.schedule.service.ScheduleAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/admin/schedules")
@RequiredArgsConstructor
public class ScheduleAdminController {

    private final ScheduleAdminService scheduleAdminService;

    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody @Valid ScheduleRequest scheduleRequest) {
        Long id = scheduleAdminService.create(scheduleRequest);
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable @Min(1L) Long id) {
        scheduleAdminService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
