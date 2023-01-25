package nextstep.member.repository;

import java.sql.PreparedStatement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.member.domain.Authority;
import nextstep.member.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDao {
    public final JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> new Member(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("phone"),
            Authority.valueOf(resultSet.getString("authority"))
    );

    public Long save(Member member) {
        String sql = "INSERT INTO member (username, password, name, phone, authority) VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getUsername());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getPhone());
            ps.setString(5, member.getAuthority().toString());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Member> findAll() {
        String sql = "SELECT id, username, password, name, phone, authority from member";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Member findById(Long id) {
        String sql = "SELECT id, username, password, name, phone, authority from member where id = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Member findByUsername(String username) {
        String sql = "SELECT id, username, password, name, phone, authority from member where username = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }

    public int deleteById(Long id) {
        String sql = "delete from member where id = ?;";
        return jdbcTemplate.update(sql, id);
    }
}
