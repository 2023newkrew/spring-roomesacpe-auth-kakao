package nextstep.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody AdminThemeRequest adminThemeRequest) {
        long id = adminService.createTheme(adminThemeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable long id) {
        adminService.deleteTheme(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/schedules")
    public ResponseEntity createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        long id = adminService.createSchedule(scheduleRequest);
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity deleteSchedule(@PathVariable long id) {
        adminService.deleteSchedule(id);

        return ResponseEntity.noContent().build();
    }
}
