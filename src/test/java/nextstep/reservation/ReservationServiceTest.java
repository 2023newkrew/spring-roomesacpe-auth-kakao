package nextstep.reservation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import nextstep.exceptions.exception.duplicate.DuplicatedReservationException;
import nextstep.exceptions.exception.notFound.ReservationNotFoundException;
import nextstep.member.Member;
import nextstep.schedule.Schedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationDao reservationDao;

    @InjectMocks
    private ReservationService reservationService;

    private Member member = Member.builder().build();
    private Schedule schedule = Schedule.builder().id(1L).build();

    @Test
    void 해당_스케줄에_예약이_존재하면_예외가_발생한다() {
        Optional<Reservation> existSchedule = Optional.of(
                Reservation.builder()
                        .id(1L)
                        .member(member)
                        .schedule(schedule)
                        .build()
        );
        given(reservationDao.findByScheduleId(schedule.getId())).willReturn(existSchedule);
        assertThatThrownBy(() -> reservationService.create(member, schedule))
                .isInstanceOf(DuplicatedReservationException.class);

    }

    @Test
    void 존재하지_않는_예약을_삭제하려고_하면_예외가_발생한다() {
        given(reservationDao.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> reservationService.deleteById(1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void 존재하지_않는_예약을_조회하려고_하면_예외가_발생한다() {
        given(reservationDao.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> reservationService.findById(1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

}
