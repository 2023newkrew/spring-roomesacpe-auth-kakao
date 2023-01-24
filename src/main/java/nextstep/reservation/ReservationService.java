package nextstep.reservation;

import nextstep.exceptions.exception.DuplicateEntityException;
import nextstep.exceptions.exception.NotExistEntityException;
import nextstep.exceptions.exception.ReservationForbiddenException;
import nextstep.member.Member;
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

    public Reservation reserve(Member member, ReservationRequest reservationRequest) {
        if (!reservationRequest.getName().equals(member.getUsername())) {
            throw new ReservationForbiddenException("예약자가 일치해야만 예약을 생성할 수 있습니다.");
        }
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NotExistEntityException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                reservationRequest.getName()
        );

        reservationDao.save(newReservation);
        return newReservation;
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NotExistEntityException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void cancelReservation(Long id, Member member) {
        Reservation reservation = findById(id);
        if (!reservation.isReservedBy(member.getName())) {
            throw new ReservationForbiddenException("예약당사자만 예약을 삭제할 수 있습니다.");
        }
        reservationDao.deleteById(id);
    }

    public Reservation findById(Long id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new NotExistEntityException();
        }
        return reservation;
    }
}
