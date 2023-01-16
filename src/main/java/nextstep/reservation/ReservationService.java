package nextstep.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.error.ApplicationException;
import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleService;
import nextstep.theme.ThemeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static nextstep.error.ErrorType.*;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final ThemeService themeService;

    public Long create(ReservationRequest reservationRequest) {
        Schedule schedule = scheduleService.findById(reservationRequest.getScheduleId());
        checkIfExistsByScheduleId(reservationRequest.getScheduleId());

        Reservation newReservation = new Reservation(schedule, reservationRequest.getName());
        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        themeService.checkExistsByThemeId(themeId);
        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long reservationId, Long memberId) {
        Reservation reservation = findByReservationId(reservationId);
        Member member = memberService.findById(memberId);

        if (!Objects.equals(reservation.getName(), member.getName())) {
            throw new ApplicationException(UNAUTHORIZED_ERROR);
        }

        reservationDao.deleteById(reservationId);
    }

    private void checkIfExistsByScheduleId(Long scheduleId) {
        if (!reservationDao.findByScheduleId(scheduleId).isEmpty()) {
            throw new ApplicationException(DUPLICATE_RESERVATION);
        }
    }

    private Reservation findByReservationId(Long reservationId) {
        Reservation reservation = reservationDao.findById(reservationId);
        if (reservation == null) {
            throw new ApplicationException(RESERVATION_NOT_FOUND);
        }
        return reservation;
    }
}
