package nextstep.dbmapper;
import nextstep.member.Member;
import nextstep.reservation.Reservation;
import nextstep.schedule.Schedule;
import nextstep.theme.Theme;
import org.springframework.jdbc.core.RowMapper;

public class H2Mapper implements DatabaseMapper {
    public RowMapper<Member> memberRowMapper() {
        return (resultSet, rowNum) ->
            new Member(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("name"),
                    resultSet.getString("phone")
            );
    }

    public RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("reservation.id"),
                new Schedule(
                        resultSet.getLong("schedule.id"),
                        new Theme(
                                resultSet.getLong("theme.id"),
                                resultSet.getString("theme.name"),
                                resultSet.getString("theme.desc"),
                                resultSet.getInt("theme.price")
                        ),
                        resultSet.getDate("schedule.date").toLocalDate(),
                        resultSet.getTime("schedule.time").toLocalTime()
                ),
                resultSet.getString("reservation.name")
        );
    }

    public RowMapper<Schedule> scheduleRowMapper() {
        return (resultSet, rowNum) -> new Schedule(
                resultSet.getLong("schedule.id"),
                new Theme(
                        resultSet.getLong("theme.id"),
                        resultSet.getString("theme.name"),
                        resultSet.getString("theme.desc"),
                        resultSet.getInt("theme.price")
                ),
                resultSet.getDate("schedule.date").toLocalDate(),
                resultSet.getTime("schedule.time").toLocalTime()
        );
    }

    public RowMapper<Theme> themeRowMapper() {
        return (resultSet, rowNum) -> new Theme(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price")
        );
    }
}

