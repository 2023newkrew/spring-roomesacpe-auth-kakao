package nextstep.admin;

import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import nextstep.schedule.ScheduleService;
import nextstep.schedule.dto.ScheduleRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/schedule")
@RequiredArgsConstructor
public class ScheduleAdminController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity createSchedule(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto) {
        Long id = scheduleService.create(scheduleRequestDto);
        return ResponseEntity.created(URI.create("/schedules/" + id))
            .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent()
            .build();
    }
}
