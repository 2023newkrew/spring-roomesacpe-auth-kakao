package nextstep.reservation;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.*;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    public final MemberDao memberDao;
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public ReservationService( MemberDao memberDao, ReservationDao reservationDao, ThemeDao themeDao, ScheduleDao scheduleDao) {
        this.memberDao = memberDao;
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
    }

    public Long create(ReservationRequest reservationRequest) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NotExistScheduleException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                reservationRequest.getName()
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

    public void deleteById(Long reservationId, Long memberId) {
        Reservation reservation = reservationDao.findById(reservationId);
        Member member = memberDao.findById(memberId);
        if(reservation == null) {
            throw new NotExistReservationException();
        }
        if(member == null) {
            throw new NotExistMemberException();
        }
        if(!reservation.isReservationMember(member)) {
            throw new AuthorizationException();
        }

        reservationDao.deleteById(reservationId);
    }
}
