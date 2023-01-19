package nextstep.reservation;

import nextstep.exception.BusinessException;
import nextstep.exception.ErrorCode;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.ThemeDao;
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

    public long create(long authId, ReservationRequest reservationRequest) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId())
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND));

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new BusinessException(ErrorCode.RESERVATION_ALREADY_EXIST_AT_THAT_TIME);
        }

        Member member = memberDao.findById(authId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        Reservation newReservation = new Reservation(schedule, member.getName());

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(long themeId, String date) {
        themeDao.findById(themeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.THEME_NOT_FOUND));

        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void cancle(long authId, long reservationId) {
        Reservation reservation = reservationDao.findById(reservationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESERVATION_NOT_FOUND));
        Member me = memberDao.findById(authId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        if (!reservation.isMyReservation(me)) {
            throw new BusinessException(ErrorCode.DELETE_FAILED_WHEN_NOT_MY_RESERVATION);
        }
        reservationDao.deleteById(reservationId);
    }
}
