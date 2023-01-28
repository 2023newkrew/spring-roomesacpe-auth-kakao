package nextstep.schedule;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import nextstep.exceptions.exception.duplicate.DuplicatedScheduleException;
import nextstep.exceptions.exception.notFound.ScheduleNotFoundException;
import nextstep.theme.Theme;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {


    @Mock
    private ScheduleDao scheduleDao;

    @InjectMocks
    private ScheduleService scheduleService;

    private Schedule schedule = Schedule
            .builder()
            .id(1L)
            .date(LocalDate.now())
            .time(LocalTime.now())
            .theme(new Theme())
            .build();

    @Test
    void 중복된_스케줄을_예약하려고_하면_예외가_발생한다() {
        given(scheduleDao.findBySchedule(schedule)).willReturn(Optional.of(schedule));
        assertThatThrownBy(() -> scheduleService.create(schedule))
                .isInstanceOf(DuplicatedScheduleException.class);
    }

    @Test
    void 존재하지_않은_스케줄을_조회하려고_하면_예외가_발생한다() {
        given(scheduleDao.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> scheduleService.findById(1L))
                .isInstanceOf(ScheduleNotFoundException.class);
    }
}
