package nextstep.member;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

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
            resultSet.getString("phone")
    );

    public Member save(Member member) {
        String sql = "INSERT INTO member (username, password, name, phone) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getUsername());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getPhone());
            return ps;

        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Member(id, member.getUsername(), member.getPassword(), member.getName(), member.getPhone());
    }

    public Member findById(Long id) {
        String sql = "SELECT id, username, password, name, phone from member where id = ? limit 1;";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Member findByUsername(String username) {
        String sql = "SELECT id, username, password, name, phone from member where username = ? limit 1;";
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }
}
