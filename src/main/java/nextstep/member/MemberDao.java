package nextstep.member;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
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
            Role.valueOf(resultSet.getString("role.name"))
    );

    public Long save(Member member) {
        Long userRoleId = findRoleId(member.getRole());
        String sql = "INSERT INTO member (username, password, name, phone, role_id) VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getUsername());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getPhone());
            ps.setLong(5, userRoleId);
            return ps;
        }, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    public Member findById(Long id) {
        String sql = "SELECT m.id, m.username, m.password, m.name, m.phone, role.name " +
                "from member as m " +
                "inner join role on m.role_id = role.id " +
                "where m.id = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Optional<Member> findByUsername(String username) {
//        String sql = "SELECT id, username, password, name, phone from member where username = ?;";
        String sql = "SELECT m.id, m.username, m.password, m.name, m.phone, role.name " +
                "from member as m " +
                "inner join role on m.role_id = role.id " +
                "where m.username = ?;";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, username));
        } catch (DataAccessException exception) {
            return Optional.empty();
        }
    }

    private Long findRoleId(Role role) {
        String sql = "SELECT id FROM role where name = '" + role.getName() + "';";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
