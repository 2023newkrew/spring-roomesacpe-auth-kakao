package nextstep.schedule.controller;

import java.util.List;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<Schedule>> showReservations(@RequestParam @Min(1L) Long themeId,
                                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String date) {
        return ResponseEntity.ok().body(scheduleService.findByThemeIdAndDate(themeId, date));
    }
}
