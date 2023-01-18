package nextstep.reservation;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberRepository;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.schedule.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReservationValidator reservationValidator;

    public ReservationService(ReservationDao reservationRepository, MemberDao memberRepository, ScheduleDao scheduleRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.scheduleRepository = scheduleRepository;
        this.reservationValidator = new ReservationValidator(reservationRepository);
    }

    public Long create(Long memberId, ReservationRequest reservationRequest) {
        reservationValidator.validateForCreate(reservationRequest.getScheduleId());

        Schedule schedule = scheduleRepository.findById(reservationRequest.getScheduleId());
        Member member = memberRepository.findById(memberId);

        return reservationRepository.save(new Reservation(schedule, member));
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        return reservationRepository.findAllByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long memberId, Long id) {
        reservationValidator.validateForDelete(memberId, id);

        reservationRepository.deleteById(id);
    }
}
