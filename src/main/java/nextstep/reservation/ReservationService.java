package nextstep.reservation;

import nextstep.member.MemberDao;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.support.DuplicateEntityException;
import nextstep.support.NotExistEntityException;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Long create(ReservationRequest reservationRequest, Long memberId) {
        Schedule schedule = scheduleDao.findById(reservationRequest.getScheduleId());
        if (schedule == null) {
            throw new NotExistEntityException();
        }

        List<Reservation> reservation = reservationDao.findByScheduleId(schedule.getId());
        if (!reservation.isEmpty()) {
            throw new DuplicateEntityException();
        }

        Reservation newReservation = new Reservation(
                schedule,
                memberDao.findById(memberId).getName()
        );

        return reservationDao.save(newReservation);
    }

    public List<ReservationResponse> findAllByThemeIdAndDate(Long themeId, String date) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new NullPointerException();
        }

        List<Reservation> results = reservationDao.findAllByThemeIdAndDate(themeId, date);

        return results.stream().map(e->e.toResponse()).collect(Collectors.toList());
    }

    public void deleteById(Long id, Long memberId) {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            throw new NotExistEntityException();
        }
        if (reservation.getName().equals(memberDao.findById(memberId).getName())) {
            reservationDao.deleteById(id);
        }
    }
}
