package nextstep.reservations.repository.reservation;

import nextstep.reservations.domain.entity.reservation.Reservation;

import java.sql.*;

public interface ReservationRepository {
    Long add(Reservation reservation);

    Reservation findById(Long id);

    int remove(final Long id);

    default PreparedStatement getInsertOnePstmt(Connection connection, Reservation reservation, String query) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(query, new String[]{"id"});
        pstmt.setDate(1, Date.valueOf(reservation.getDate()));
        pstmt.setTime(2, Time.valueOf(reservation.getTime()));
        pstmt.setString(3, reservation.getName());
        pstmt.setString(4, reservation.getTheme().getName());
        pstmt.setString(5, reservation.getTheme().getDesc());
        pstmt.setInt(6, reservation.getTheme().getPrice());
        pstmt.setLong(7, reservation.getMember().getId());

        return pstmt;
    }
}
