package nextstep.reservation;

import nextstep.common.Authenticated;
import nextstep.common.LoginMember;
import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.dto.ReservationResponse;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleService;
import nextstep.theme.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final ThemeService themeService;


    public ReservationController(ReservationService reservationService, MemberService memberService,
                                 ScheduleService scheduleService, ThemeService themeService) {
        this.reservationService = reservationService;
        this.memberService = memberService;
        this.scheduleService = scheduleService;
        this.themeService = themeService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@Authenticated LoginMember loginMember, @RequestBody ReservationRequest reservationRequest) {
        Member member = memberService.findById(loginMember.getId());
        Schedule schedule = scheduleService.findById(reservationRequest.getScheduleId());
        Long id = reservationService.create(member, schedule);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readReservations(@RequestParam Long themeId, @RequestParam String date) {
        themeService.findById(themeId);

        List<ReservationResponse> results = reservationService
                .findAllByThemeIdAndDate(themeId, date)
                .stream()
                .map(ReservationResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@Authenticated LoginMember loginMember, @PathVariable Long id) {
        reservationService.deleteById(id, loginMember.getId());
        return ResponseEntity.noContent().build();
    }
}
