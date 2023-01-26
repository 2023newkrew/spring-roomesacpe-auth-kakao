package nextstep.repository;

import static nextstep.repository.ThemeJdbcSql.DELETE_BY_ID_STATEMENT;
import static nextstep.repository.ThemeJdbcSql.INSERT_INTO_STATEMENT;
import static nextstep.repository.ThemeJdbcSql.SELECT_ALL_STATEMENT;
import static nextstep.repository.ThemeJdbcSql.SELECT_INTO_BY_ID;

import java.sql.PreparedStatement;
import java.util.List;
import nextstep.entity.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ThemeDao {
    private final JdbcTemplate jdbcTemplate;

    public ThemeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Theme> rowMapper = (resultSet, rowNum) -> Theme.giveId(Theme.builder()
                    .desc(resultSet.getString("desc"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getInt("price"))
                    .build()
            , resultSet.getLong("id"));

    public Long save(Theme theme) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_INTO_STATEMENT, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;

        }, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    public Theme findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_INTO_BY_ID, rowMapper, id);
    }

    public List<Theme> findAll() {
        return jdbcTemplate.query(SELECT_ALL_STATEMENT, rowMapper);
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE_BY_ID_STATEMENT, id);
    }
}
