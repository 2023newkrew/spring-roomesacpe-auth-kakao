package nextstep.admin;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Optional;

@Component
public class AdminDao {
    public final JdbcTemplate jdbcTemplate;

    public AdminDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Admin> rowMapper = (resultSet, rowNum) -> new Admin(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("phone")
    );

    public Long save(Admin admin) {
        String sql = "INSERT INTO ADMIN (username, password, name, phone) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            ps.setString(3, admin.getName());
            ps.setString(4, admin.getPhone());
            return ps;
        }, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    public Admin findById(Long id) {
        String sql = "SELECT id, username, password, name, phone from ADMIN where id = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Optional<Admin> findByUsername(String username) {
        String sql = "SELECT id, username, password, name, phone from ADMIN where username = ?;";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, username));
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }
}
