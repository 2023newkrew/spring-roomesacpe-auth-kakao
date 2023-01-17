package nextstep.reservation;

import nextstep.exception.DuplicateEntityException;
import nextstep.exception.NotExistEntityException;
import nextstep.exception.NotQualifiedMemberException;
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

    public Long create(ReservationRequest reservationRequest, Member member) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId())
                .orElseThrow(() -> new NotExistEntityException(
                        "존재하지 않는 스케줄입니다 - " + reservationRequest.getScheduleId()));

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException("이미 예약된 스케줄입니다 - " + schedule.getId());
        }

        Reservation newReservation = new Reservation(schedule, member);

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        themeDao.findById(themeId)
                .orElseThrow(() -> new NotExistEntityException("존재하지 않는 테마입니다 - " + themeId));
        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id, Member member) {
        Reservation reservation = reservationDao.findById(id)
                .orElseThrow(() -> new NotExistEntityException("존재하지 않는 예약입니다 - " + id));
        if(!reservation.checkOwner(member)) {
            throw new NotQualifiedMemberException("다른 사용자의 예약을 삭제할 수 없습니다");
        }
        reservationDao.deleteById(id);
    }
}
