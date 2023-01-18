package nextstep.member;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

@Component
@RequiredArgsConstructor
public class MemberDao {

    public final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> new Member(
        resultSet.getLong("id"),
        resultSet.getString("username"),
        resultSet.getString("password"),
        resultSet.getString("name"),
        resultSet.getString("phone")
    );

    public Long save(Member member) {
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

        return keyHolder.getKey()
            .longValue();
    }

    public Optional<Member> findByUsername(String username) {
        String sql = "SELECT id, username, password, name, phone from member where username = ?;";
        Member member = null;
        try {
            member = jdbcTemplate.queryForObject(sql, rowMapper, username);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(member);
    }

    public Optional<Member> findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id, username, password, name, phone from member where username = ? AND password = ?;";
        Member member = null;
        try {
            member = jdbcTemplate.queryForObject(sql, rowMapper, username, password);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(member);
    }
}
