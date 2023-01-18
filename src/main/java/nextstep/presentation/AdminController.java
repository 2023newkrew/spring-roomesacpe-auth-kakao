package nextstep.presentation;

import lombok.RequiredArgsConstructor;
import nextstep.dto.request.ScheduleRequest;
import nextstep.dto.request.ThemeRequest;
import nextstep.service.ScheduleService;
import nextstep.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final ThemeService themeService;
    private final ScheduleService scheduleService;

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest);

        return ResponseEntity.created(URI.create("/themes/" + id))
                .build();
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.delete(id);

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/schedules")
    public ResponseEntity createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(scheduleRequest);

        return ResponseEntity.created(URI.create("/schedules/" + id))
                .build();
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent()
                .build();
    }

}
