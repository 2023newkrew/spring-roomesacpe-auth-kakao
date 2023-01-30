package nextstep.reservation;

import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@JdbcTest
@SpringBootTest
@Transactional
public class ReservationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void serviceTest() {
        ReservationDao reservationDao = new ReservationDao(jdbcTemplate);
        ScheduleDao scheduleDao = new ScheduleDao(jdbcTemplate);
        ThemeDao themeDao = new ThemeDao(jdbcTemplate);

        Long id1 = themeDao.save(new Theme("테마 이름500", "테마 설명", 22_000));
        Long id2 = themeDao.save(new Theme("테마 이름600", "테마 설명", 22_000));
        Long id3 = themeDao.save(new Theme("테마 이름700", "테마 설명", 22_000));
        scheduleDao.save(new Schedule(themeDao.findById(id1), LocalDate.parse("2000-01-01"), LocalTime.parse("10:00")));
        scheduleDao.save(new Schedule(themeDao.findById(id3), LocalDate.parse("2000-01-01"), LocalTime.parse("10:00")));


        ReservationRequest reservationRequest = new ReservationRequest(2L, "userA");

        ReservationService reservationService = new ReservationService(reservationDao, themeDao, scheduleDao);

        List<Schedule> schedules = scheduleDao.findAll();
        System.out.println(schedules.size());
        for (Schedule schedule : schedules) {
            System.out.println(schedule.getTheme().getName());
        }

        Long id = reservationService.create(reservationRequest);
        assertThat(reservationDao.findById(id).getSchedule().getTheme().getName()).isEqualTo("테마 이름700");
    }

}
