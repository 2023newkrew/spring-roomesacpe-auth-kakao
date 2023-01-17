package nextstep.member;

public enum MemberJdbcSql {
    INSERT("INSERT INTO member (username, password, name, phone) VALUES (?, ?, ?, ?);"),
    SELECT_BY_ID("SELECT id, username, password, name, phone from member where id = ?;"),
    SELECT_BY_USERNAME_AND_PASSWORD("SELECT id, username, password, name, phone from member where username = ? AND password = ?;");

    private final String sql;

    MemberJdbcSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return sql;
    }
}
