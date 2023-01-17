package nextstep.member.dao;

import nextstep.member.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RowMapper<MemberEntity> rowMapper = (resultSet, rowNum) -> new MemberEntity(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("phone")
    );

    @Autowired
    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(MemberEntity memberEntity) {
        String sql = "INSERT INTO member (username, password, name, phone) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, memberEntity.getUsername());
            ps.setString(2, memberEntity.getPassword());
            ps.setString(3, memberEntity.getName());
            ps.setString(4, memberEntity.getPhone());
            return ps;

        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElse(-1L);
    }

    public MemberEntity findById(Long id) {
        String sql = "SELECT id, username, password, name, phone from member where id = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public MemberEntity findByUsername(String username) {
        String sql = "SELECT id, username, password, name, phone from member where username = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }

    public MemberEntity findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id, username, password, name, phone from member where username = ? and password = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper, username, password);
    }
}
