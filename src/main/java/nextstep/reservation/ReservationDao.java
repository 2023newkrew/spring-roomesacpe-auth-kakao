package nextstep.reservation;

import nextstep.member.Member;
import nextstep.schedule.Schedule;
import nextstep.theme.Theme;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static nextstep.reservation.ReservationJdbcSql.*;

@Component
public class ReservationDao {

    public final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
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
            new Member(resultSet.getLong("member.id"),
                    resultSet.getString("member.username"),
                    resultSet.getString("member.password"),
                    resultSet.getString("member.name"),
                    resultSet.getString("member.phone"))
    );

    public Long save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT.toString(), new String[]{"id"});
            ps.setLong(1, reservation.getSchedule().getId());
            ps.setLong(2, reservation.getMember().getId());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        return jdbcTemplate.query(ReservationJdbcSql.SELECT_BY_THEME_ID_AND_DATE.toString(),
                rowMapper, themeId, Date.valueOf(date));
    }

    public Optional<Reservation> findById(Long id) {
        try {
            Reservation reservation = jdbcTemplate.queryForObject(SELECT_BY_RESERVATION_ID.toString(), rowMapper, id);
            return Optional.of(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Reservation> findByScheduleId(Long id) {
        try {
            return jdbcTemplate.query(ReservationJdbcSql.SELECT_BY_SCHEDULE_ID.toString(), rowMapper, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(ReservationJdbcSql.DELETE_BY_ID.toString(), id);
    }
}
