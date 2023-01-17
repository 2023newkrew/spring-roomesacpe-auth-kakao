package nextstep.theme;

public enum ThemeJdbcSql {
    INSERT("INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);"),
    SELECT_BY_ID("SELECT id, name, desc, price from theme where id = ?;"),
    SELECT_ALL("SELECT id, name, desc, price from theme;"),
    DELETE_BY_ID("DELETE FROM reservation where id = ?;");

    private final String sql;

    ThemeJdbcSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return sql;
    }
}
