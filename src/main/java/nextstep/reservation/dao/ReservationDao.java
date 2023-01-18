package nextstep.reservation.dao;

import nextstep.reservation.entity.ReservationEntity;
import nextstep.schedule.entity.Schedule;
import nextstep.theme.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class ReservationDao {

    public final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationEntity> rowMapper = (resultSet, rowNum) -> new ReservationEntity(
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

    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(ReservationEntity reservationEntity) {
        String sql = "INSERT INTO reservation (schedule_id, member_id) VALUES (?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, reservationEntity.getSchedule().getId());
            ps.setLong(2, reservationEntity.getMemberId());
            return ps;

        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElse(-1L)
                ;
    }

    public List<ReservationEntity> findAllByThemeIdAndDate(Long themeId, String date) {
        String sql = "SELECT reservation.id, reservation.schedule_id, reservation.member_id, schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price " +
                "FROM reservation " +
                "INNER JOIN schedule ON reservation.schedule_id = schedule.id " +
                "INNER JOIN theme ON schedule.theme_id = theme.id " +
                "WHERE theme.id = ? AND schedule.date = ?;";

        return jdbcTemplate.query(sql, rowMapper, themeId, Date.valueOf(date));
    }

    public ReservationEntity findById(Long id) {
        String sql = "SELECT reservation.id, reservation.schedule_id, reservation.member_id, schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price " +
                "FROM reservation " +
                "INNER JOIN schedule ON reservation.schedule_id = schedule.id " +
                "INNER JOIN theme ON schedule.theme_id = theme.id " +
                "WHERE reservation.id = ?;";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<ReservationEntity> findByScheduleId(Long id) {
        String sql = "SELECT reservation.id, reservation.schedule_id, reservation.member_id, schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price " +
                "FROM reservation " +
                "INNER JOIN schedule ON reservation.schedule_id = schedule.id " +
                "INNER JOIN theme ON schedule.theme_id = theme.id " +
                "WHERE schedule.id = ?;";

        return jdbcTemplate.query(sql, rowMapper, id);
    }

    public boolean existsByIdAndMemberId(Long id, Long memberId) {
        String sql = "SELECT * FROM reservation WHERE id = ? AND member_id = ?";

        return !jdbcTemplate.queryForList(sql, id, memberId).isEmpty();
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?;";

        return jdbcTemplate.update(sql, id);
    }
}
