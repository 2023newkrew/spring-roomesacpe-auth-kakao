package nextstep.auth.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import nextstep.auth.AuthenticatedMember;
import nextstep.member.Member;
import nextstep.member.MemberRequest;
import nextstep.member.MemberService;
import nextstep.reservation.ReservationRequest;
import nextstep.reservation.ReservationService;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleRequest;
import nextstep.schedule.ScheduleService;
import nextstep.theme.ThemeRequest;
import nextstep.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;

    private final ReservationService reservationService;
    private final ScheduleService scheduleService;
    private final ThemeService themeService;

    @PostMapping("/schedules")
    public ResponseEntity<Object> createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(
                scheduleRequest.toEntityWithTheme(
                        themeService.findById(scheduleRequest.getThemeId())
                )
        );
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest.toEntity());
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reservations")
    public ResponseEntity<Object> createReservation(
            @RequestBody ReservationRequest reservationRequest,
            @AuthenticatedMember Member member
    ) {
        Schedule schedule = scheduleService.findById(reservationRequest.getScheduleId());
        Long id = reservationService.create(member, schedule);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/members")
    public ResponseEntity<Object> createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(memberRequest.toEntity());
        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Object> deleteMember(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
