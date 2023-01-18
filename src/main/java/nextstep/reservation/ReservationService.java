package nextstep.reservation;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final MemberDao memberDao;
    private final ScheduleDao scheduleDao;
    private final ReservationValidator reservationValidator;

    public ReservationService(ReservationDao reservationDao, MemberDao memberDao, ScheduleDao scheduleDao) {
        this.reservationDao = reservationDao;
        this.memberDao = memberDao;
        this.scheduleDao = scheduleDao;
        this.reservationValidator = new ReservationValidator(reservationDao);
    }

    public Long create(Long memberId, ReservationRequest reservationRequest) {
        reservationValidator.validateForCreate(reservationRequest.getScheduleId());

        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        Member member = memberDao.findById(memberId);

        return reservationDao.save(new Reservation(schedule, member));
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        return reservationDao.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long memberId, Long id) {
        reservationValidator.validateForDelete(memberId, id);

        reservationDao.deleteById(id);
    }
}
