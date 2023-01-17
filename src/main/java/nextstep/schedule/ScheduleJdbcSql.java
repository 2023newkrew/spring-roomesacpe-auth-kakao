package nextstep.schedule;

public enum ScheduleJdbcSql {
    INSERT("INSERT INTO schedule (theme_id, date, time) VALUES (?, ?, ?);"),
    SELECT_BY_ID("SELECT schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price " +
            "from schedule " +
            "inner join theme on schedule.theme_id = theme.id " +
            "where schedule.id = ?;"),
    SELECT_BY_THEME_ID_AND_DATE("SELECT schedule.id, schedule.theme_id, schedule.date, schedule.time, theme.id, theme.name, theme.desc, theme.price " +
            "from schedule " +
            "inner join theme on schedule.theme_id = theme.id " +
            "where schedule.theme_id = ? and schedule.date = ?;"),

    DELETE_BY_ID("DELETE FROM schedule where id = ?;");

    private final String sql;

    ScheduleJdbcSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return sql;
    }
}
