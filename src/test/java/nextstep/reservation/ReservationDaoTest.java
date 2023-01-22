package nextstep.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class ReservationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationDao reservationDao;
    private ScheduleDao scheduleDao;
    private MemberDao memberDao;
    private ThemeDao themeDao;
    private Reservation reservation = Reservation.builder()
            .member(Member.builder()
                    .name("name")
                    .phone("")
                    .password("1234")
                    .username("username")
                    .build())
            .schedule(Schedule.builder()
                    .date(LocalDate.now())
                    .time(LocalTime.now())
                    .theme(Theme.builder()
                            .desc("desc")
                            .name("name")
                            .price(123)
                            .build())
                    .build())
            .build();


    @BeforeEach
    void setUp() {
        reservationDao = new ReservationDao(jdbcTemplate);
        scheduleDao = new ScheduleDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
        themeDao = new ThemeDao(jdbcTemplate);
        Long id = themeDao.save(reservation.getSchedule().getTheme());
        reservation.getSchedule().getTheme().setId(id);
        id = scheduleDao.save(reservation.getSchedule());
        reservation.getSchedule().setId(id);
        id = memberDao.save(reservation.getMember());
        reservation.getMember().setId(id);
    }

    @Test
    void 예약에_성공하면_아이디를_반환한다() {
        assertThat(reservationDao.save(reservation)).isInstanceOf(Long.class);
    }

    @Test
    void 테마아이디와_날짜를_통해_예약을_가져올_수_있다() {
        reservationDao.save(reservation);
        Long themeId = reservation.getSchedule().getTheme().getId();
        String strDate = String.valueOf(reservation.getSchedule().getDate());
        assertThat(reservationDao.findAllByThemeIdAndDate(themeId, strDate))
                .isInstanceOf(List.class);
    }

    @Test
    void 아이디를_통해_예약을_조회할_수_있다() {
        Long id = reservationDao.save(reservation);
        assertThat(reservationDao.findById(id).get()).isInstanceOf(Reservation.class);
    }
}
