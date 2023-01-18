package nextstep.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ReservationRequestDto;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.exception.DuplicateEntityException;
import nextstep.support.exception.NotExistEntityException;
import nextstep.support.exception.UnauthorizedException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public Long create(ReservationRequestDto reservationRequestDto, String username) {
        Schedule schedule = scheduleDao.findById(reservationRequestDto.getScheduleId())
            .orElseThrow(NullPointerException::new);

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
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NullPointerException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id, String username) {
        Reservation reservation = reservationDao.findById(id)
            .orElseThrow(() -> new NotExistEntityException("예약번호가 유효하지 않습니다."));

        if (reservation.getName() != username) {
            throw new UnauthorizedException("자신의 예약이 아닙니다.");
        }

        reservationDao.deleteById(id);
    }
}
