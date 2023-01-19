package nextstep.reservation;

import nextstep.auth.JwtTokenProvider;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.DuplicateEntityException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

import static nextstep.auth.principal.MemberAuthenticationPrincipalArgumentResolver.Bearer;

@Service
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;
    private final JwtTokenProvider jwtTokenProvider;


    public ReservationService(ReservationDao reservationDao, ThemeDao themeDao, ScheduleDao scheduleDao, JwtTokenProvider jwtTokenProvider) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Long create(ReservationRequest reservationRequest, String authorization) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
        throw new NullPointerException();
        }


        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }
        String accessToken = authorization.substring(Bearer.length());
        String username = jwtTokenProvider.getPrincipal(accessToken);

        Reservation newReservation = new Reservation(
                schedule,
                username
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
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new NullPointerException();
        }

        reservationDao.deleteById(id);
    }
}
