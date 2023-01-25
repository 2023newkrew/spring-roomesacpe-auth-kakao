package nextstep.schedule.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import nextstep.reservation.Reservation;
import nextstep.reservation.ReservationDao;
import nextstep.schedule.dto.request.ScheduleRequest;
import nextstep.schedule.repository.ScheduleDao;
import nextstep.theme.domain.Theme;
import nextstep.theme.repository.ThemeDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleAdminService {

    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;
    private final ReservationDao reservationDao;

    public Long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId());
        return scheduleDao.save(scheduleRequest.toEntity(theme));
    }

    public void deleteById(Long id) {
        List<Reservation> reservations = reservationDao.findByScheduleId(id);
        if (reservations.size() > 0) {
            throw new RoomReservationException(ErrorCode.SCHEDULE_CANT_BE_DELETED);
        }
        scheduleDao.deleteById(id);
    }
}
