package nextstep.theme;

import nextstep.Mapper.DatabaseMapper;
import nextstep.Mapper.H2Mapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class ThemeDao {
    private final JdbcTemplate jdbcTemplate;
    private final DatabaseMapper databaseMapper;

    public ThemeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.databaseMapper = new H2Mapper();
    }

    public Long save(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Boolean findByNameAndPrice(ThemeRequest theme){
        String sql = "SELECT EXISTS(SELECT 1 FROM theme WHERE name=? and price=?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, theme.getName(), theme.getPrice());
    }

    public Optional<List<Theme>> findById(Long id) {
        String sql = "SELECT id, name, desc, price from theme where id = ?;";
        return Optional.of(jdbcTemplate.query(sql, databaseMapper.themeRowMapper(), id));
    }

    public List<Theme> findAll() {
        String sql = "SELECT id, name, desc, price from theme;";
        return jdbcTemplate.query(sql, databaseMapper.themeRowMapper());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM theme where id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
