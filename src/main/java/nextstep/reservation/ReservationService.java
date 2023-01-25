package nextstep.reservation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.exception.DuplicatedEntityException;
import nextstep.exception.NotExistEntityException;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public Long create(ReservationRequest reservationRequest) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NotExistEntityException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicatedEntityException();
        }

        Reservation newReservation = Reservation.builder()
                .schedule(schedule)
                .name(reservationRequest.getName())
                .build();

        return reservationDao.save(newReservation);
    }

    public Reservation findById(Long id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new NotExistEntityException();
        }
        return reservation;
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NotExistEntityException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new NotExistEntityException();
        }
        reservationDao.deleteById(id);
    }
}
