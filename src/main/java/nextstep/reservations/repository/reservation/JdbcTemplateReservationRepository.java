package nextstep.reservations.repository.reservation;

import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.domain.entity.theme.Theme;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Primary
public class JdbcTemplateReservationRepository extends ReservationSqlRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long add(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> getInsertOnePstmt(connection, reservation, INSERT_ONE_QUERY), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Reservation findById(Long id) {
        RowMapper<Reservation> rowMapper = getReservationRowMapper();
        return Objects.requireNonNull(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, rowMapper, id));
    }

    @Override
    public int remove(final Long id) {
        return jdbcTemplate.update(REMOVE_BY_ID_QUERY, id);
    }

    private static RowMapper<Reservation> getReservationRowMapper() {
        return (rs, rowNum) -> Reservation.builder()
                .id(rs.getLong("id"))
                .date(rs.getDate("date").toLocalDate())
                .time(rs.getTime("time").toLocalTime())
                .name(rs.getString("name"))
                .theme(Theme.builder()
                        .name(rs.getString("theme_name"))
                        .desc(rs.getString("theme_desc"))
                        .price(rs.getInt("theme_price"))
                        .build())
                .build();
    }
}
