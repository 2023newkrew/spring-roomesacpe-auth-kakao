package nextstep.reservation;

import java.sql.SQLException;
import nextstep.schedule.Schedule;
import nextstep.theme.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ReservationDao {

    public final JdbcTemplate jdbcTemplate;
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
            resultSet.getLong("reservation.member_id")
    );

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Long getIdFromSequence() {
        String sql = "SELECT nextval('seq_reservation')";
        return jdbcTemplate.query(sql,
                rs -> {
                    if (rs.next()) {
                        return rs.getLong(1);
                    } else {
                        throw new SQLException();
                    }
                });
    }

    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (id, schedule_id, member_id) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, getIdFromSequence());
            ps.setLong(2, reservation.getSchedule().getId());
            ps.setLong(3, reservation.getMemberId());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        String sql =
                "SELECT reservation.id, reservation.schedule_id, reservation.member_id, schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price "
                        +
                        "from reservation " +
                        "inner join schedule on reservation.schedule_id = schedule.id " +
                        "inner join theme on schedule.theme_id = theme.id " +
                        "where theme.id = ? and schedule.date = ?;";

        return jdbcTemplate.query(sql, rowMapper, themeId, Date.valueOf(date));
    }

    public Reservation findById(Long id) {
        String sql =
                "SELECT reservation.id, reservation.schedule_id, reservation.member_id, schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price "
                        +
                        "from reservation " +
                        "inner join schedule on reservation.schedule_id = schedule.id " +
                        "inner join theme on schedule.theme_id = theme.id " +
                        "where reservation.id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Reservation> findByScheduleId(Long id) {
        String sql =
                "SELECT reservation.id, reservation.schedule_id, reservation.member_id, schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price "
                        +
                        "from reservation " +
                        "inner join schedule on reservation.schedule_id = schedule.id " +
                        "inner join theme on schedule.theme_id = theme.id " +
                        "where schedule.id = ?;";

        try {
            return jdbcTemplate.query(sql, rowMapper, id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean existsByIdAndMemberId(Long id, Long memberId) {
        String sql = "SELECT * FROM reservation WHERE id = ? AND member_id = ?";
        try {
            return !jdbcTemplate.queryForList(sql, id, memberId).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation where id = ?;";
        return jdbcTemplate.update(sql, id);
    }
}
