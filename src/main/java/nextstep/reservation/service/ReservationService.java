package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.member.model.Member;
import nextstep.reservation.model.Reservation;
import nextstep.reservation.dao.ReservationDao;
import nextstep.reservation.model.ReservationRequest;
import nextstep.schedule.model.Schedule;
import nextstep.schedule.dao.ScheduleDao;
import nextstep.auth.support.AuthenticationException;
import nextstep.support.DuplicateEntityException;
import nextstep.theme.model.Theme;
import nextstep.theme.dao.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public Long create(ReservationRequest reservationRequest) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NullPointerException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());

        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                reservationRequest.getMemberName()
        );

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date, Member loginMember) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NullPointerException();
        }

        return reservationDao.findAllByThemeIdAndDateAndMemberName(themeId, date, loginMember.getMemberName());
    }

    public void deleteById(Long id, Member loginMember) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new NullPointerException();
        }
        if (!reservation.getMemberName().equals(loginMember.getMemberName())) {
            throw new AuthenticationException();
        }

        reservationDao.deleteById(id);
    }
}
