package nextstep.reservations.repository.reservation;

public abstract class ReservationSqlRepository implements ReservationRepository{
    protected static final String INSERT_ONE_QUERY = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price, member_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    protected static final String FIND_BY_ID_QUERY = "SELECT * FROM reservation WHERE id = ?";
    protected static final String REMOVE_BY_ID_QUERY = "DELETE FROM reservation WHERE id = ?";
}
