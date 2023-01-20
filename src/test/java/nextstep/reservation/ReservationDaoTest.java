package nextstep.reservation;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberDaoTest;
import nextstep.member.Role;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleDao;
import nextstep.theme.Theme;
import nextstep.theme.ThemeDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@JdbcTest
class ReservationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ThemeDao themeDao;
    private ScheduleDao scheduleDao;
    private MemberDao memberDao;
    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        themeDao = new ThemeDao(jdbcTemplate);
        scheduleDao = new ScheduleDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
        reservationDao = new ReservationDao(jdbcTemplate);
    }

    @DisplayName("예약 저장 시, 멤버 중 해당 이름을 가지고 있는 사람이 없다면 DataIntegrity 를 위반하여 예외가 발생한다.")
    @Test
    void save() {
        // given
        Theme theme = new Theme(1L, "name", "desc", 1000);
        themeDao.save(theme);

        Schedule schedule = new Schedule(1L, theme, LocalDate.now(), LocalTime.now());
        scheduleDao.save(schedule);

        Member member = new Member(1L, "username", "password", "name", "phone", Role.MEMBER);
        memberDao.save(member);

        Reservation reservation = new Reservation(1L, schedule, "invalidMemberName");

        // when
        assertThatCode(() -> reservationDao.save(reservation))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("예약 저장 시, 멤버 중 해당 이름을 가지고 있는 멤버가 있다면 정상 저장된다.")
    @Test
    void saveWithValidMemberName() {
        // given
        Theme theme = new Theme(1L, "name", "desc", 1000);
        themeDao.save(theme);

        Schedule schedule = new Schedule(1L, theme, LocalDate.now(), LocalTime.now());
        scheduleDao.save(schedule);

        String memberName = "name";
        Member member = new Member(1L, "username", "password", memberName, "phone", Role.MEMBER);
        memberDao.save(member);

        Reservation reservation = new Reservation(1L, schedule, memberName);

        // when
        Long reservationId = reservationDao.save(reservation);

        // then
        assertThat(reservationId).isNotNull();
    }
}