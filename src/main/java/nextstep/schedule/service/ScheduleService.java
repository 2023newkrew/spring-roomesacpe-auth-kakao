package nextstep.schedule.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import nextstep.reservation.Reservation;
import nextstep.reservation.ReservationDao;
import nextstep.schedule.repository.ScheduleDao;
import nextstep.schedule.dto.request.ScheduleRequest;
import nextstep.schedule.domain.Schedule;
import nextstep.theme.domain.Theme;
import nextstep.theme.repository.ThemeDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleDao scheduleDao;

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return scheduleDao.findByThemeIdAndDate(themeId, date);
    }
}
