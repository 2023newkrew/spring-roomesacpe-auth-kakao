package nextstep.domain.reservation;

import nextstep.domain.schedule.Schedule;
import nextstep.domain.schedule.ScheduleDao;
import nextstep.domain.member.MemberDao;
import nextstep.domain.member.Member;
import nextstep.interfaces.reservation.dto.ReservationRequest;
import nextstep.interfaces.exception.DuplicateEntityException;
import nextstep.interfaces.exception.NotExistEntityException;
import nextstep.domain.theme.Theme;
import nextstep.domain.theme.ThemeDao;
import org.springframework.stereotype.Service;
import nextstep.interfaces.exception.AuthorizationException;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ThemeDao themeDao;
    private final ScheduleDao scheduleDao;
    private final MemberDao memberDao;

    public ReservationService(ReservationDao reservationDao, ThemeDao themeDao, ScheduleDao scheduleDao, MemberDao memberDao) {
        this.reservationDao = reservationDao;
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
        this.memberDao = memberDao;
    }

    public Long create(ReservationRequest reservationRequest, Long loginId) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NullPointerException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException("이미 존재하는 예약입니다.");
        }

        Reservation newReservation = new Reservation(
                schedule, memberDao.findById(loginId)

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

    public void deleteById(Long id, Long memberId) {
        Reservation reservation = reservationDao.findById(id);
        Member member = memberDao.findById(memberId);
        if (reservation == null) {
            throw new NotExistEntityException("해당 아이디가 존재하지 않습니다.");
        }
        if (!reservation.getMember().getId().equals(member.getId())){
            throw new AuthorizationException("권한이 없습니다.");
        }
        reservationDao.deleteById(id);
    }
}
