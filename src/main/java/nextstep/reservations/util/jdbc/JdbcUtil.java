package nextstep.reservations.util.jdbc;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class JdbcUtil {
    public static Connection getConnection() {
        Connection connection;
        Yaml yaml = new Yaml();

        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.yml")) {
            HashMap<String, Object> config = yaml.load(fileInputStream);
            HashMap<String, Object> springConfig = (HashMap<String, Object>) config.get("spring");
            HashMap<String, String> datasourceConfig = (HashMap<String, String>) springConfig.get("datasource");

            connection = DriverManager.getConnection(datasourceConfig.get("url"),
                    datasourceConfig.get("username"),
                    datasourceConfig.get("password"));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}
