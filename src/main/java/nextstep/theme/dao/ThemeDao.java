package nextstep.theme.dao;

import nextstep.theme.entity.ThemeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class ThemeDao {

    private final RowMapper<ThemeEntity> rowMapper = (resultSet, rowNum) -> new ThemeEntity(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getInt("price")
    );
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ThemeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(ThemeEntity themeEntity) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, themeEntity.getName());
            ps.setString(2, themeEntity.getDesc());
            ps.setInt(3, themeEntity.getPrice());
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElse(-1L)
                ;
    }

    public ThemeEntity findById(Long id) {
        String sql = "SELECT id, name, desc, price FROM theme WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<ThemeEntity> findAll() {
        String sql = "SELECT id, name, desc, price FROM theme;";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM theme WHERE id = ?;";

        return jdbcTemplate.update(sql, id);
    }
}
