package nextstep.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.member.LoginMember;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.exception.*;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
    public final ReservationDao reservationDao;
    public final ThemeDao themeDao;
    public final ScheduleDao scheduleDao;

    public Long create(ReservationRequest reservationRequest, LoginMember member) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId()).orElseThrow(
                () -> new ScheduleException(RoomEscapeExceptionCode.NOT_FOUND_SCHEDULE)
        );

        if (!reservationDao.findByScheduleId(schedule.getId()).isEmpty()) {
            throw new ScheduleException(RoomEscapeExceptionCode.SCHEDUL_ALREADY_RESERVED);
        }

        Reservation reservation = Reservation.builder()
                .schedule(schedule)
                .name(reservationRequest.getName())
                .memberId(member.getId())
                .build();
        return reservationDao.save(reservation);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        themeDao.findById(themeId).orElseThrow(
                () -> new ThemeException(RoomEscapeExceptionCode.NOT_FOUND_THEME)
        );

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id, LoginMember member) {
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

