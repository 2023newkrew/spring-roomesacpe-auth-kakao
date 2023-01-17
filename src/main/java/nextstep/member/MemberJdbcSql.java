package nextstep.member;

public final class MemberJdbcSql {
    public static final String INSERT_INTO_STATEMENT = "INSERT INTO member (username, password, name, phone) VALUES (?, ?, ?, ?);";
    public static final String SELECT_BY_ID_STATEMENT = "SELECT id, username, password, name, phone from member where id = ?;";
    public static final String SELECT_BY_USERNAME_AND_PASSWORD_STATEMENT = "SELECT id, username, password, name, phone from member where username = ? AND password = ?;";

    private MemberJdbcSql() {}
}