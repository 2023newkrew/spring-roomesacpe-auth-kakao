package nextstep.member;

import java.sql.SQLException;
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
    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> new Member(
            Long.parseLong(resultSet.getString("id")),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("phone"),
            MemberRole.valueOf(resultSet.getString("role"))
    );

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Long getIdFromSequence() {
        String sql = "SELECT nextval('seq_member')";
        return jdbcTemplate.query(sql,
                rs -> {
                    if (rs.next()) {
                        return rs.getLong(1);
                    } else {
                        throw new SQLException();
                    }
                });
    }

    public Long save(Member member) {
        String sql = "INSERT INTO member (id, username, password, name, phone, role) VALUES (?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, getIdFromSequence());
            ps.setString(2, member.getUsername());
            ps.setString(3, member.getPassword());
            ps.setString(4, member.getName());
            ps.setString(5, member.getPhone());
            ps.setString(6, member.getRole().name());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Member findById(Long id) {
        String sql = "SELECT id, username, password, name, phone, role from member where id = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Optional<Member> findByUsername(String username) {
        String sql = "SELECT id, username, password, name, phone, role from member where username = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, username));
    }

    public void updateRole(Long id, MemberRole memberRole) {
        String sql = "UPDATE member SET role = ? where id = ?";
        jdbcTemplate.update(sql, memberRole.name(), id);
    }
}
