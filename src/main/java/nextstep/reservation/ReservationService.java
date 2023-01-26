package nextstep.reservation;

import static nextstep.common.exception.ExceptionMessage.INVALID_RESERVATION_ID;
import static nextstep.common.exception.ExceptionMessage.INVALID_SCHEDULE_ID;
import static nextstep.common.exception.ExceptionMessage.INVALID_THEME_ID;
import static nextstep.common.exception.ExceptionMessage.UNAUTHORIZED_RESERVATION;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.common.exception.DuplicateEntityException;
import nextstep.common.exception.NotExistEntityException;
import nextstep.common.exception.UnauthorizedException;
import nextstep.reservation.dto.ReservationRequestDto;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public Long create(ReservationRequestDto reservationRequestDto, String username) {
        Schedule schedule = scheduleDao.findById(reservationRequestDto.getScheduleId())
            .orElseThrow(() -> new NotExistEntityException(INVALID_SCHEDULE_ID.getMessage()));

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
            schedule,
            username);

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        themeDao.findById(themeId)
            .orElseThrow(() -> new NotExistEntityException(INVALID_THEME_ID.getMessage()));
        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id, String username) {
        Reservation reservation = reservationDao.findById(id)
            .orElseThrow(() -> new NotExistEntityException(INVALID_RESERVATION_ID.getMessage()));

        if (reservation.getName() != username) {
            throw new UnauthorizedException(UNAUTHORIZED_RESERVATION.getMessage());
        }

        reservationDao.deleteById(id);
    }
}
