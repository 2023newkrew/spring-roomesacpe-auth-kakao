package nextstep.member;

import java.sql.PreparedStatement;
import javax.sql.DataSource;
import nextstep.auth.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {
    public final JdbcTemplate jdbcTemplate;

    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> Member.builder()
            .id(resultSet.getLong("id"))
            .username(resultSet.getString("username"))
            .password(resultSet.getString("password"))
            .role(Role.valueOf(resultSet.getString("role")))
            .name(resultSet.getString("name"))
            .phone(resultSet.getString("phone"))
            .build();

    public Long save(Member member) {
        String sql = "INSERT INTO member (username, password, role, name, phone) VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getUsername());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getRole().name());
            ps.setString(4, member.getName());
            ps.setString(5, member.getPhone());
            return ps;

        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Member findById(Long id) {
        String sql = "SELECT id, username, password, role, name, phone from member where id = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Member findByUsername(String username) {
        String sql = "SELECT id, username, password, role, name, phone from member where username = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }

    public boolean countByUsernameAndPassword(String username, String password) {
        String sql = "SELECT count(*) from member where username = ? and password = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username, password) != 0;
    }
}
