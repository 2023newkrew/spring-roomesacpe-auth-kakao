package nextstep.member;

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
            resultSet.getString("phone")
    );

    public Long save(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(MemberJdbcSql.INSERT.toString(), new String[]{"id"});
            ps.setString(1, member.getUsername());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getPhone());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<Member> findById(Long id) {
        return jdbcTemplate.query(MemberJdbcSql.SELECT_BY_ID.toString(), rowMapper, id).stream().findAny();
    }

    public Optional<Member> findByUsernameAndPassword(String username, String password) {
        return jdbcTemplate.query(MemberJdbcSql.SELECT_BY_USERNAME_AND_PASSWORD.toString(),
                rowMapper, username, password).stream().findAny();
    }
}
