package nextstep.reservation;

import com.sun.jdi.request.DuplicateRequestException;
import nextstep.auth.JwtTokenProvider;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
            throw new NullPointerException("스케줄을 찾을 수 없음");
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateRequestException("이미 해당 스케줄로 등록되어 있음");
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

    public void deleteById(Long id, String authorization) {
        String accessToken = authorization.substring(Bearer.length());
        String username = jwtTokenProvider.getPrincipal(accessToken);

        Reservation reservation = reservationDao.findById(id);

        if (reservation == null) {
            throw new NullPointerException();
        }
       if (!Objects.equals(reservation.getName(), username)){
            throw new IllegalArgumentException("삭제 권한이 없습니다");
        }

        reservationDao.deleteById(id);
    }
}
