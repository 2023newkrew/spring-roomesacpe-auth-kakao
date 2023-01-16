package nextstep.schedule;

import nextstep.theme.Theme;
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

import static nextstep.schedule.ScheduleJdbcSql.*;

@Component
public class ScheduleDao {


    private final JdbcTemplate jdbcTemplate;

    public ScheduleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Schedule> rowMapper = (resultSet, rowNum) -> new Schedule(
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

    public Long save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_INTO_STATEMENT, new String[]{"id"});
            ps.setLong(1, schedule.getTheme().getId());
            ps.setDate(2, Date.valueOf(schedule.getDate()));
            ps.setTime(3, Time.valueOf(schedule.getTime()));
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Schedule findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_STATEMENT, rowMapper, id);
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {
        return jdbcTemplate.query(SELECT_BY_THEME_ID_AND_DATE_STATEMENT, rowMapper, themeId, Date.valueOf(LocalDate.parse(date)));
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID_STATEMENT, id);
    }

}
