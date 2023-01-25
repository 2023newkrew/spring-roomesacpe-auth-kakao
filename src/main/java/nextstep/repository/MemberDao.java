package nextstep.repository;

import static nextstep.repository.MemberJdbcSql.INSERT_INTO_STATEMENT;
import static nextstep.repository.MemberJdbcSql.SELECT_BY_ID_STATEMENT;
import static nextstep.repository.MemberJdbcSql.SELECT_BY_USERNAME_AND_PASSWORD_STATEMENT;

import java.sql.PreparedStatement;
import java.util.Optional;
import nextstep.entity.Member;
import nextstep.entity.MemberRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    public final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> Member.giveId(Member.builder()
            .username(resultSet.getString("username"))
            .phone(resultSet.getString("phone"))
            .password(resultSet.getString("password"))
            .name(resultSet.getString("name"))
            .role(MemberRole.findRole(resultSet.getInt("role")))
            .build(), resultSet.getLong("id"));

    public Long save(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_INTO_STATEMENT, new String[]{"id"});
            ps.setString(1, member.getUsername());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());
            ps.setString(4, member.getPhone());
            ps.setInt(5, member.getRole().getValue());
            return ps;

        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<Member> findById(Long id) {
        return jdbcTemplate.query(SELECT_BY_ID_STATEMENT, rowMapper, id).stream().findAny();
    }

    public Optional<Member> findByUsernameAndPassword(String username, String password) {
        return jdbcTemplate.query(SELECT_BY_USERNAME_AND_PASSWORD_STATEMENT, rowMapper, username, password).stream()
                .findAny();
    }

}
