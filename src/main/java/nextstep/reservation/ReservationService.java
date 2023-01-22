package nextstep.reservation;

import nextstep.exceptions.exception.duplicate.DuplicatedReservationException;
import nextstep.exceptions.exception.notFound.ReservationNotFoundException;
import nextstep.member.Member;
import nextstep.schedule.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    public final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Long create(Member member, Schedule schedule) {
        reservationDao
                .findByScheduleId(schedule.getId())
                .ifPresent(res -> {
                    throw new DuplicatedReservationException();
                });

        return reservationDao.save(
                Reservation.builder()
                        .schedule(schedule)
                        .member(member)
                        .build()
        );
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        reservationDao.findById(id)
                .orElseThrow(ReservationNotFoundException::new);
        reservationDao.deleteById(id);
    }

    public Reservation findById(Long id) {
        return reservationDao.findById(id)
                .orElseThrow(ReservationNotFoundException::new);

    }

}
