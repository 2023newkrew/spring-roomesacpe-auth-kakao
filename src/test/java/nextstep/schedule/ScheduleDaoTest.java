package nextstep.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class ScheduleDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ScheduleDao scheduleDao;
    private ThemeDao themeDao;
    private Theme theme = Theme.builder()
            .name("name")
            .price(1)
            .desc("desc")
            .build();

    private Schedule schedule = Schedule.builder()
            .theme(theme)
            .date(LocalDate.now())
            .time(LocalTime.now())
            .build();

    @BeforeEach
    void setUp() {
        scheduleDao = new ScheduleDao(jdbcTemplate);
        themeDao = new ThemeDao(jdbcTemplate);
        Long id = themeDao.save(theme);
        theme.setId(id);
    }

    @Test
    void 저장후에_아이디를_반환한다() {
        assertThat(scheduleDao.save(schedule)).isInstanceOf(Long.class);
    }

    @Test
    void 아이디를_통해_스케쥴을_조회할_수_있다() {
        Long id = scheduleDao.save(schedule);
        assertThat(scheduleDao.findById(id).get()).isInstanceOf(Schedule.class);
    }

    @Test
    void 없는_아이디를_조회하면_빈_값을_반환한다() {
        assertThat(scheduleDao.findById(1L)).isEqualTo(Optional.empty());
    }

    @Test
    void 테마아이디와_날짜로_스케줄을_조회하면_리스트로_반환한다() {
        assertThat(scheduleDao.findByThemeIdAndDate(theme.getId(), String.valueOf(schedule.getDate())))
                .isInstanceOf(java.util.List.class);
    }
}
