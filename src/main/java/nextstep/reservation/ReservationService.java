package nextstep.reservation;

import nextstep.member.Member;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.exception.*;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
            throw new RoomEscapeException(ErrorCode.DUPLICATE_RESERVATION);
        }

        Reservation newReservation = new Reservation(
                schedule,
                reservationRequest.getName(),
                member.getId()
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

    public void deleteReservation(Long id, Member member) {
        Reservation reservation = getReservationOrThrowException(id);
        checkOwner(reservation, member);

        reservationDao.deleteById(id);
    }

    private Reservation getReservationOrThrowException(Long reservationId){
        Reservation reservation = reservationDao.findById(reservationId);

        if (reservation == null) {
            throw new RoomEscapeException(ErrorCode.NO_SUCH_RESERVATION);
        }

        return reservation;
    }

    private void checkOwner(Reservation reservation, Member member){
        if(!Objects.equals(member.getId(), reservation.getMemberId())) {
            throw new RoomEscapeException(ErrorCode.NOT_RESERVATION_OWNER);
        }
    }
}
