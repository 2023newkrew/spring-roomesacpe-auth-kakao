package nextstep.reservations.repository.theme;

import nextstep.reservations.domain.entity.theme.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ThemeRepository {
    private static final String INSERT_ONE_QUERY = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM theme";
    private static final String REMOVE_BY_ID_QUERY = "DELETE FROM theme WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long add(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(INSERT_ONE_QUERY, new String[]{"id"});
            pstmt.setString(1, theme.getName());
            pstmt.setString(2, theme.getDesc());
            pstmt.setLong(3, theme.getPrice());
            return pstmt;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Theme> findAll() {
        RowMapper<Theme> rowMapper = (rs, rowNum) -> Theme.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .desc(rs.getString("desc"))
                .price(rs.getInt("price"))
                .build();

        return jdbcTemplate.query(FIND_ALL_QUERY, rowMapper);
    }

    public int remove(final Long id) {
        return jdbcTemplate.update(REMOVE_BY_ID_QUERY, id);
    }
}
