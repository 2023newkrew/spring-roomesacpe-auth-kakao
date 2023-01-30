package nextstep.admin;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminDao {
    public final JdbcTemplate jdbcTemplate;

    public AdminDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Admin> rowMapper = (resultSet, rowNum) -> new Admin(
            resultSet.getLong("id"),
            resultSet.getBoolean("admin")
    );

    public Admin findById(Long id) {
        String sql = "SELECT id, admin from member where id = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
