package nextstep.reservation;

import nextstep.member.Member;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.exception.DuplicateEntityException;
import nextstep.exception.NotExistEntityException;
import nextstep.exception.UnauthorizedAccessException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    public final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Long create(Member member, Schedule schedule) {
        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException("이미 해당 스케줄에 예약이 존재합니다.");
        }

        return reservationDao.save(new Reservation(schedule, member));
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
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
        return reservationDao.findById(id)
                .orElseThrow(() -> new NotExistEntityException("해당 아이디의 예약이 존재하지 않습니다."));
    }
}
