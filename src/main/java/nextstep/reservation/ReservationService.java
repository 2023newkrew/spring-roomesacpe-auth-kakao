package nextstep.reservation;

import nextstep.exceptions.exception.DuplicatedReservationException;
import nextstep.exceptions.exception.ObjectNotFoundException;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

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

    public Long create(ReservationRequest reservationRequest) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new ObjectNotFoundException("해당 스케쥴을 찾을 수 없습니다.");
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicatedReservationException("해당 스케쥴에 예약이 존재합니다.");
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
            throw new ObjectNotFoundException("테마를 찾을 수 없습니다.");
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        Reservation reservation = reservationDao.findById(id);
        checkNullReservation(reservation);
        reservationDao.deleteById(id);
    }

    public Reservation findById(Long id) {
        Reservation reservation = reservationDao.findById(id);
        checkNullReservation(reservation);
        return reservation;
    }

    private static void checkNullReservation(Reservation reservation) {
        if (reservation == null) {
            throw new ObjectNotFoundException("해당 예약을 찾을 수 없습니다.");
        }
    }
}
