package nextstep.reservation;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.AuthorizationException;
import nextstep.support.DuplicateEntityException;
import nextstep.support.NotExistEntityException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;
    public final MemberDao memberDao;

    public ReservationService(ReservationDao reservationDao, ThemeDao themeDao, ScheduleDao scheduleDao, MemberDao memberDao) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
        this.memberDao = memberDao;
    }

    @Transactional
    public Long create(String username, ReservationRequest reservationRequest) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NotExistEntityException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Member member = memberDao.findByUsername(username);

        Reservation newReservation = new Reservation(
                schedule,
                member.getName()
        );

        return reservationDao.save(newReservation);
    }

    @Transactional
    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NotExistEntityException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    @Transactional
    public void deleteById(String username, Long id) {
        Reservation reservation = reservationDao.findById(id);
        Member member = memberDao.findByUsername(username);
        if (reservation == null || member == null) {
            throw new NotExistEntityException();
        }

        if (!Objects.equals(member.getName(), reservation.getName())) {
            throw new AuthorizationException();
        }

        reservationDao.deleteById(id);
    }
}
