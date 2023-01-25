package nextstep.reservation;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.AuthorizationException;
import nextstep.support.DuplicateEntityException;
import nextstep.support.NotExistEntityException;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public Long create(String username, ReservationRequest reservationRequest) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId())
                .orElseThrow(NotExistEntityException::new);

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Member member = memberDao.findByUsername(username)
                .orElseThrow(NotExistEntityException::new);

        Reservation newReservation = new Reservation(
                schedule,
                member.getName()
        );

        return reservationDao.save(newReservation);
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(String username, Long id) {
        Reservation reservation = reservationDao.findById(id)
                .orElseThrow(NotExistEntityException::new);

        Member member = memberDao.findByUsername(username)
                .orElseThrow(NotExistEntityException::new);

        if (!Objects.equals(member.getName(), reservation.getName())) {
            throw new AuthorizationException("자신의 예약만 삭제할 수 있습니다.");
        }

        reservationDao.deleteById(id);
    }
}
