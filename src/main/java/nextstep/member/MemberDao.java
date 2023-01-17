package nextstep.member;

import nextstep.permission.Authority;
import nextstep.support.DuplicateEntityException;
import org.springframework.dao.DataAccessException;
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
            resultSet.getString("phone"),
            Authority.valueOf(resultSet.getString("authority"))
    );

    public Long save(Member member) {
        String sql = "INSERT INTO member (username, password, name, phone, authority) VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, member.getUsername());
                ps.setString(2, member.getPassword());
                ps.setString(3, member.getName());
                ps.setString(4, member.getPhone());
                ps.setString(5, member.getAuthority().name());
                return ps;

            }, keyHolder);
        } catch(DataAccessException e){
            throw new DuplicateEntityException();
        }

        return keyHolder.getKey().longValue();
    }

    public Member findById(Long id) {
        String sql = "SELECT * from member where id = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Member findByUsername(String username) {
        String sql = "SELECT * from member where username = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }
}
