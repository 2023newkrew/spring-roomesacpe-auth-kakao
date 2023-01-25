package nextstep.repository;

public final class MemberJdbcSql {
    public static final String INSERT_INTO_STATEMENT = "INSERT INTO member (username, password, name, phone, role) VALUES (?, ?, ?, ?, ?);";
    public static final String SELECT_BY_ID_STATEMENT = "SELECT id, username, password, name, phone, role from member where id = ?;";
    public static final String SELECT_BY_USERNAME_AND_PASSWORD_STATEMENT = "SELECT id, username, password, name, phone, role from member where username = ? AND password = ?;";
    public static final String DELETE_BY_ID = "DELETE FROM MEMBER WHERE id = ?;";
    private MemberJdbcSql() {}
}
