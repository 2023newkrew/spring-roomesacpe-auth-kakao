package nextstep.reservation.dao;

import nextstep.reservation.entity.ReservationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class ReservationDao {

    public final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationEntity> rowMapper = (resultSet, rowNum) -> new ReservationEntity(
            resultSet.getLong("reservation.id"),
            resultSet.getLong("reservation.schedule_id"),
            resultSet.getLong("reservation.member_id")
    );

    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(ReservationEntity reservationEntity) {
        String sql = "INSERT INTO reservation (schedule_id, member_id) VALUES (?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, reservationEntity.getScheduleId());
            ps.setLong(2, reservationEntity.getMemberId());
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElse(-1L)
                ;
    }

    public ReservationEntity findById(Long id) {
        String sql = "SELECT id, schedule_id, member_id " +
                "FROM reservation " +
                "WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<ReservationEntity> findByScheduleId(Long scheduleId) {
        String sql = "SELECT id, schedule_id, member_id " +
                "FROM reservation " +
                "WHERE schedule_id = ?;";

        return jdbcTemplate.query(sql, rowMapper, scheduleId);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation " +
                "WHERE id = ?;";

        return jdbcTemplate.update(sql, id);
    }
}
