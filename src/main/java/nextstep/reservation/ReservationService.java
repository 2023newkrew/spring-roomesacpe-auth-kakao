package nextstep.reservation;

import com.sun.jdi.request.DuplicateRequestException;
import nextstep.auth.JwtTokenProvider;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import javax.naming.NotContextException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static nextstep.auth.Interceptor.LoginInterceptor.bearer;
import static nextstep.config.Messages.*;


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
        List<Schedule> schedules = scheduleDao.findById(reservationRequest.getScheduleId());

        if (schedules.isEmpty()) {
            throw new NullPointerException(NOT_FOUND_SCHEDULE.getMessage() + reservationRequest.getScheduleId());
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedules.get(0).getId());
        if (!reservation.isEmpty()) {  // <- 하나만 수용.. / -> size()
            throw new DuplicateRequestException(ALREADY_REGISTERED_RESERVATION.getMessage());
        }

        String accessToken = authorization.substring(bearer.length());
        String username = jwtTokenProvider.getPrincipal(accessToken);
        Reservation newReservation = new Reservation(
                schedules.get(0),
                username
        );
        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date, String authorization) {
        Optional<List<Theme>> themeList = themeDao.findById(themeId);
        if (themeList.isEmpty() || themeList.get().isEmpty()) {
            throw new NullPointerException();
        }

        String accessToken = authorization.substring(bearer.length());
        String username = jwtTokenProvider.getPrincipal(accessToken);

        return reservationDao.findAllByThemeIdAndDate(username, themeId, date);
    }

    public void deleteById(Long id, String authorization) throws NotContextException {
        String accessToken = authorization.substring(bearer.length());
        String username = jwtTokenProvider.getPrincipal(accessToken);

        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new NotContextException(RESERVATION_NOT_FOUND.getMessage());
        }
       if (!Objects.equals(reservation.getName(), username)){
            throw new AuthorizationServiceException(NOT_PERMISSION_DELETE.getMessage());
        }
        reservationDao.deleteById(id);
    }
}
