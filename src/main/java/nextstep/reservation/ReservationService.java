package nextstep.reservation;

import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.DuplicateEntityException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;
import nextstep.auth.JwtTokenProvider;

import java.util.List;

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

    public Long create(ReservationRequest reservationRequest, String token) {

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        if (!jwtTokenProvider.validateToken(token)) {
            // TODO: throw exception. Token invalid
            throw new IllegalArgumentException("토큰 잘못됨");
        }

        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NullPointerException();
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

    public void deleteById(Long id) {
        reservationDao.deleteById(id);
    }

    public void deleteById(Long id, String token) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        if (!jwtTokenProvider.validateToken(token)) {
            // TODO: throw exception. Token invalid
            System.out.println("invalid token");
            throw new IllegalArgumentException("토큰 잘못됨");
        }
        Reservation reservation = reservationDao.findById(id);

        if (reservation == null) {
            throw new NullPointerException();
        }

        if (!reservation.getName().equals(jwtTokenProvider.getPrincipal(token))) {
            throw new IllegalArgumentException("본인 예약이 아님");
        }

        reservationDao.deleteById(id);
    }
}
