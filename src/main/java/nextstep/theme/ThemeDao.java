package nextstep.theme;

import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ThemeDao {
    private final RowMapper<Theme> rowMapper = (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getInt("price")
    );
    private JdbcTemplate jdbcTemplate;

    public ThemeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Long getIdFromSequence() {
        String sql = "SELECT nextval('seq_theme')";
        return jdbcTemplate.query(sql,
                rs -> {
                    if (rs.next()) {
                        return rs.getLong(1);
                    } else {
                        throw new SQLException();
                    }
                });
    }

    public Long save(Theme theme) {
        String sql = "INSERT INTO theme (id, name, desc, price) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, getIdFromSequence());
            ps.setString(2, theme.getName());
            ps.setString(3, theme.getDesc());
            ps.setInt(4, theme.getPrice());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Theme findById(Long id) {
        String sql = "SELECT id, name, desc, price from theme where id = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Theme> findAll() {
        String sql = "SELECT id, name, desc, price from theme;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public int delete(Long id) {
        String sql = "DELETE FROM theme where id = ?;";
        return jdbcTemplate.update(sql, id);
    }
}
