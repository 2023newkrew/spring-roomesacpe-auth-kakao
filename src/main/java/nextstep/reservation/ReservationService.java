package nextstep.reservation;

import nextstep.login.LoginMember;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.*;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Long create(Long scheduleId, LoginMember loginMember) {
        Schedule schedule = scheduleDao.findById(scheduleId).orElseThrow(ScheduleNotFoundException::new);
        reservationDao.findByScheduleId(schedule.getId())
                .ifPresent(s -> {throw new DuplicateReservationException();});
        Reservation newReservation = new Reservation(
                schedule,
                loginMember.getUsername()
        );

        return reservationDao.save(newReservation);
    }

    public List<ReservationResponse> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        return reservationDao.findAllByThemeIdAndDate(themeId, date)
                .stream()
                .map(reservation -> ReservationResponse.of(reservation))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id, LoginMember loginMember) {
        Reservation reservation = reservationDao.findById(id).orElseThrow(ReservationNotFoundException::new);
        if (!reservation.getName().equals(loginMember.getUsername())) {
            throw new UnauthorizedException();
        }
        reservationDao.deleteById(id);
    }
}
