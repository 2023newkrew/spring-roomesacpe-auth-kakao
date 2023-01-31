package nextstep.reservation.domain;

import nextstep.member.persistence.MemberDao;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.persistence.ReservationDao;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.persistence.ScheduleDao;
import nextstep.support.AuthorizationException;
import nextstep.support.DuplicateEntityException;
import nextstep.support.NotExistEntityException;
import nextstep.theme.domain.Theme;
import nextstep.theme.persistence.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public final MemberDao memberDao;

    public ReservationService(ReservationDao reservationDao, ThemeDao themeDao, ScheduleDao scheduleDao,
                              MemberDao memberDao) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
        this.memberDao = memberDao;
    }

    public Long create(ReservationRequest reservationRequest, Long memberId) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId()).orElseThrow(NotExistEntityException::new);
        if (schedule == null) {
            throw new NullPointerException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                reservationRequest.getName(),
                memberId
        );

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId).orElseThrow(NotExistEntityException::new);
        if (theme == null) {
            throw new NullPointerException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id, Long memberId) {
        Reservation reservation = reservationDao.findById(id).orElseThrow(NotExistEntityException::new);
        if (reservation == null) {
            throw new NullPointerException();
        }
        if (!Objects.equals(reservation.getMemberId(), memberId)) {
            throw new AuthorizationException();
        }

        reservationDao.deleteById(id);
    }
}