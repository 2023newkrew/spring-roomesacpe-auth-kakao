package nextstep.theme.repository;

public class ThemeJdbcSql {

    public static final String INSERT_INTO_STATEMENT = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";

    public static final String SELECT_INTO_BY_ID = "SELECT id, name, desc, price from theme where id = ?;";

    public static final String SELECT_ALL_STATEMENT = "SELECT id, name, desc, price from theme;";

    public static final String DELETE_BY_ID_STATEMENT = "DELETE FROM theme where id = ?;";

}
