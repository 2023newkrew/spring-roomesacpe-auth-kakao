package nextstep.reservation.service;

import nextstep.member.entity.MemberEntity;
import nextstep.member.dao.MemberDao;
import nextstep.reservation.dao.ReservationDao;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.entity.Reservation;
import nextstep.schedule.entity.Schedule;
import nextstep.schedule.dao.ScheduleDao;
import nextstep.global.exception.DuplicateEntityException;
import nextstep.global.exception.NotExistEntityException;
import nextstep.theme.entity.Theme;
import nextstep.theme.dao.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;
    public final MemberDao memberDao;

    public ReservationService(ReservationDao reservationDao, ThemeDao themeDao, ScheduleDao scheduleDao, MemberDao memberDao) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
        this.memberDao = memberDao;
    }

    public Long create(ReservationRequest reservationRequest, String username) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NullPointerException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        MemberEntity member = memberDao.findByUsername(username)
                .orElseThrow(NullPointerException::new);

        Reservation newReservation = new Reservation(
                schedule,
                member.getName()
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

    public void deleteById(String username, Long id) {
        MemberEntity member = memberDao.findByUsername(username)
                .orElseThrow(NullPointerException::new);

        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new NullPointerException();
        }

        if (!reservation.getName().equals(member.getName())) {
            throw new NotExistEntityException();
        }

        reservationDao.deleteById(id);
    }
}
