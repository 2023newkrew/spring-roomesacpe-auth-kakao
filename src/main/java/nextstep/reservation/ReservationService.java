package nextstep.reservation;

import nextstep.member.Member;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.exception.AuthorizationExcpetion;
import nextstep.support.exception.ReservationException;
import nextstep.support.exception.ScheduleException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public ReservationService(ReservationDao reservationDao, ThemeDao themeDao, ScheduleDao scheduleDao) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
    }

    public Long create(ReservationRequest reservationRequest, Member member) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new ScheduleException("스케줄이 존재하지 않습니다.");
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new ReservationException("예약이 이미 존재합니다.");
        }

        Reservation newReservation = new Reservation(
                schedule,
                reservationRequest.getName(),
                member.getId()
        );

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NullPointerException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id, Member member) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new ReservationException("예약이 존재하지 않습니다.");
        }
        Long memberId = reservation.getMemberId();
        if (!Objects.equals(member.getId(), memberId)) {
            throw new AuthorizationExcpetion("본인의 예약만 삭제할 수 있습니다.");
        }
        reservationDao.deleteById(id);
    }
}
