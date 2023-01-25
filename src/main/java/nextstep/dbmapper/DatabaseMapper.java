package nextstep.dbmapper;

import nextstep.member.Member;
import nextstep.reservation.Reservation;
import nextstep.schedule.Schedule;
import nextstep.theme.Theme;
import org.springframework.jdbc.core.RowMapper;

public interface DatabaseMapper {
    RowMapper<Member> memberRowMapper();
    RowMapper<Reservation> reservationRowMapper();
    RowMapper<Schedule> scheduleRowMapper();
    RowMapper<Theme> themeRowMapper();
}
