package nextstep.reservation;

import nextstep.auth.AuthenticatedMember;
import nextstep.exceptions.exception.notFound.ReservationForbiddenException;
import nextstep.member.Member;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ScheduleService scheduleService;

    public ReservationController(ReservationService reservationService,
            ScheduleService scheduleService) {
        this.reservationService = reservationService;
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Object> createReservation(
            @RequestBody ReservationRequest reservationRequest,
            @AuthenticatedMember Member member
    ) {
        Schedule schedule = scheduleService.findById(reservationRequest.getScheduleId());
        Long id = reservationService.create(member, schedule);
        if (!reservationRequest.getName().equals(member.getName())) {
            throw new ReservationForbiddenException("예약자가 일치해야만 예약을 생성할 수 있습니다.");
        }
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations(
            @RequestParam Long themeId,
            @RequestParam String date,
            @AuthenticatedMember Member member
    ) {
        List<Reservation> reservations = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(
            @PathVariable Long id,
            @AuthenticatedMember Member member
    ) {
        Reservation reservation = reservationService.findById(id);
        if (!reservation.getMember().getId().equals(member.getId())) {
            throw new ReservationForbiddenException("예약당사자만 예약을 삭제할 수 있습니다.");
        }
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
