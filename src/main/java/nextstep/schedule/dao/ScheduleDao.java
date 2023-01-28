package nextstep.schedule.dao;

import nextstep.schedule.entity.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduleDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ScheduleEntity> rowMapper = (resultSet, rowNum) -> new ScheduleEntity(
            resultSet.getLong("schedule.id"),
            resultSet.getLong("schedule.theme_id"),
            resultSet.getDate("schedule.date").toLocalDate(),
            resultSet.getTime("schedule.time").toLocalTime()
    );

    @Autowired
    public ScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(ScheduleEntity scheduleEntity) {
        String sql = "INSERT INTO schedule (theme_id, date, time) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, scheduleEntity.getThemeId());
            ps.setDate(2, Date.valueOf(scheduleEntity.getDate()));
            ps.setTime(3, Time.valueOf(scheduleEntity.getTime()));
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElse(-1L)
                ;
    }

    public ScheduleEntity findById(Long id) {
        String sql = "SELECT schedule.id, schedule.theme_id, schedule.date, schedule.time " +
                "FROM schedule " +
                "WHERE schedule.id = ?;";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<ScheduleEntity> findByThemeIdAndDate(Long themeId, String date) {
        String sql = "SELECT schedule.id, schedule.theme_id, schedule.date, schedule.time " +
                "FROM schedule " +
                "WHERE schedule.theme_id = ? AND schedule.date = ?;";

        return jdbcTemplate.query(sql, rowMapper, themeId, Date.valueOf(LocalDate.parse(date)));
    }

    public int deleteById(Long id) {

        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?;", id);
    }

}
