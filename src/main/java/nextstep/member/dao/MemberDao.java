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
            resultSet.getString("phone"),
            resultSet.getString("role")
    );

    @Autowired
    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(MemberEntity memberEntity) {
        String sql = "INSERT INTO member (username, password, name, phone, role) VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, memberEntity.getUsername());
            ps.setString(2, memberEntity.getPassword());
            ps.setString(3, memberEntity.getName());
            ps.setString(4, memberEntity.getPhone());
            ps.setString(5, Optional.ofNullable(memberEntity.getRole()).orElse("USER"));
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElse(-1L);
    }

    public MemberEntity findById(Long id) {
        String sql = "SELECT id, username, password, name, phone, role FROM member WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public MemberEntity findByUsername(String username) {
        String sql = "SELECT id, username, password, name, phone, role FROM member WHERE username = ?;";

        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }
}
