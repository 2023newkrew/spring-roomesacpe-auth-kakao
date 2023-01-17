package nextstep.reservation;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.DuplicateEntityException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    public final ReservationDao reservationDao;
    public final MemberDao memberDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public ReservationService(ReservationDao reservationDao, MemberDao memberDao, ThemeDao themeDao, ScheduleDao scheduleDao) {
        this.reservationDao = reservationDao;
        this.memberDao = memberDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
    }

    public Long create(Long memberId, ReservationRequest reservationRequest) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        Member member = memberDao.findById(memberId);
        if (schedule == null) {
            throw new NullPointerException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                member
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

    public void deleteById(Long memberId, Long id) {
        Reservation reservation = reservationDao.findById(id);
        if (!memberId.equals(reservation.getMember().getId())) {
            throw new RuntimeException();
        }
        if (reservation == null) {
            throw new NullPointerException();
        }

        reservationDao.deleteById(id);
    }
}
