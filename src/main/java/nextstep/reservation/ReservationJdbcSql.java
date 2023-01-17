package nextstep.reservation;

public enum ReservationJdbcSql {
    INSERT("INSERT INTO reservation (schedule_id, member_id) VALUES (?, ?);"),
    SELECT_BY_THEME_ID_AND_DATE("SELECT reservation.id, " +
            "member.id, member.username, member.password, member.phone, member.name, " +
            "schedule.id, schedule.theme_id, schedule.date, schedule.time, " +
            "theme.id, theme.name, theme.desc, theme.price " +
            "from reservation " +
            "inner join schedule on reservation.schedule_id = schedule.id " +
            "inner join theme on schedule.theme_id = theme.id " +
            "inner join member on reservation.member_id = member.id " +
            "where theme.id = ? and schedule.date = ?;"),
    SELECT_BY_RESERVATION_ID("SELECT reservation.id, " +
            "member.id, member.username, member.password, member.phone, member.name, " +
            "schedule.id, schedule.theme_id, schedule.date, schedule.time, " +
            "theme.id, theme.name, theme.desc, theme.price " +
            "from reservation " +
            "inner join schedule on reservation.schedule_id = schedule.id " +
            "inner join theme on schedule.theme_id = theme.id " +
            "inner join member on reservation.member_id = member.id " +
            "where reservation.id = ?;"),
    SELECT_BY_SCHEDULE_ID("SELECT reservation.id, " +
            "member.id, member.username, member.password, member.phone, member.name, " +
            "schedule.id, schedule.theme_id, schedule.date, schedule.time, " +
            "theme.id, theme.name, theme.desc, theme.price " +
            "from reservation " +
            "inner join schedule on reservation.schedule_id = schedule.id " +
            "inner join theme on schedule.theme_id = theme.id " +
            "inner join member on reservation.member_id = member.id " +
            "where schedule.id = ?;"),
    DELETE_BY_ID("DELETE FROM reservation where id = ?;");

    private final String sql;

    ReservationJdbcSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return sql;
    }
}
