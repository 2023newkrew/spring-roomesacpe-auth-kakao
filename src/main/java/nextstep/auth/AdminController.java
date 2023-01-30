package nextstep.auth;


import nextstep.member.MemberRequest;
import nextstep.member.MemberService;
import nextstep.schedule.ScheduleRequest;
import nextstep.schedule.ScheduleService;
import nextstep.theme.ThemeRequest;
import nextstep.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static nextstep.support.Messages.UPDATE_ADMIN;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ScheduleService scheduleService;
    private final ThemeService themeService;
    private final MemberService memberService;

    public AdminController(ScheduleService scheduleService, ThemeService themeService, MemberService memberService) {
        this.scheduleService = scheduleService;
        this.themeService = themeService;
        this.memberService = memberService;
    }

    @PostMapping("/schedules")
    public ResponseEntity<String> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(scheduleRequest);
        return ResponseEntity.created(URI.create("/schedules/" + id)).body("Location: /schedules/" + id);
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/themes")
    public ResponseEntity<String> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest);
        return ResponseEntity.created(URI.create("/themes/" + id)).body("Location: /themes/" + id);
    }

    @DeleteMapping("themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> updateAdmin(@RequestBody MemberRequest memberRequest) {
        memberService.updateAdmin(memberRequest);
        return ResponseEntity.ok().body(UPDATE_ADMIN.getMessage() + memberRequest.getName());
    }
}
