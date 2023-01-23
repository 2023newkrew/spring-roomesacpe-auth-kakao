package nextstep.reservation;

import nextstep.member.Member;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.exception.*;
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
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId()).orElseThrow(
                () -> new ScheduleException(RoomEscapeExceptionCode.NOT_FOUND_SCHEDULE)
        );

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new ScheduleException(RoomEscapeExceptionCode.SCHEDUL_ALREADY_RESERVED);
        }

        Reservation newReservation = Reservation.builder()
                .schedule(schedule)
                .name(reservationRequest.getName())
                .memberId(member.getId())
                .build();
        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        themeDao.findById(themeId).orElseThrow(
                () -> new ThemeException(RoomEscapeExceptionCode.NOT_FOUND_THEME)
        );

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id, Member member) {
        Reservation reservation = reservationDao.findById(id).orElseThrow(
                () -> new ReservationException(RoomEscapeExceptionCode.NOT_FOUND_RESERVATION)
        );
        Long memberId = reservation.getMemberId();
        if (!Objects.equals(member.getId(), memberId)) {
            throw new AuthorizationExcpetion(RoomEscapeExceptionCode.NOT_OWN_RESERVATION);
        }
        reservationDao.deleteById(id);
    }
}
