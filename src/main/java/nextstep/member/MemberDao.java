package nextstep.member;

import nextstep.common.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class MemberDao {
    public final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> new Member(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("phone"),
            Role.valueOf(resultSet.getString("role"))
    );

    public Long save(Member member) {
        String sql = "INSERT INTO member (username, password, name, phone, role) VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getUsername());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getPhone());
            ps.setString(5, member.getRole().name());
            return ps;
        }, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    public List<Member> findAll() {
        String sql = "SELECT * from member";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Member> findById(Long id) {
        String sql = "SELECT id, username, password, name, phone, role from member where id = ?;";
        return jdbcTemplate.query(sql, rowMapper, id).stream().findAny();
    }

    public Optional<Member> findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id, username, password, name, phone, role from member where username = ? AND password = ?;";
        return jdbcTemplate.query(sql, rowMapper, username, password).stream().findAny();
    }

    public Integer updateRoleById(Long id, Role role) {
        String sql = "UPDATE member SET role = ? WHERE  id = ?";
        return jdbcTemplate.update(sql, role.name(), id);
    }
}
