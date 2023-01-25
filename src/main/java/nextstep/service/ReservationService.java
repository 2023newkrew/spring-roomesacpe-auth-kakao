package nextstep.service;

import java.util.List;
import nextstep.dto.reservation.ReservationRequest;
import nextstep.entity.Member;
import nextstep.entity.Reservation;
import nextstep.entity.Schedule;
import nextstep.entity.Theme;
import nextstep.exception.DuplicateEntityException;
import nextstep.exception.NotFoundException;
import nextstep.exception.UnauthorizedAccessException;
import nextstep.repository.ReservationDao;
import nextstep.repository.ScheduleDao;
import nextstep.repository.ThemeDao;
import org.springframework.stereotype.Service;

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

    public Long create(ReservationRequest reservationRequest, Member member) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NullPointerException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = Reservation.builder()
                .schedule(schedule)
                .member(member)
                .build();

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NullPointerException();
        }

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id, Long memberId) {
        canDelete(id, memberId);
        reservationDao.deleteById(id);
    }

    public void canDelete(Long id, Long requestUserId) {
        Long memberId = findMemberIdById(id);
        if (!requestUserId.equals(memberId)) {
            throw new UnauthorizedAccessException("자신의 예약이 아닙니다.");
        }
    }

    private Long findMemberIdById(Long id) {
        Reservation reservation = findById(id);
        return reservation.getMember().getId();
    }

    public Reservation findById(Long id) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new NotFoundException("해당 아이디의 예약이 존재하지 않습니다.");
        }
        return reservation;
    }
}
