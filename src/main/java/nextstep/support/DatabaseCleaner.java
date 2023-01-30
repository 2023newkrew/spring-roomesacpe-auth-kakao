package nextstep.support;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Component
public class DatabaseCleaner implements InitializingBean {

    private final JdbcTemplate jdbcTemplate;
    private List<String> tableNames;

    public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        tableNames = jdbcTemplate.queryForList("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC'", String.class);
    }

    @Transactional
    public void clear() {
        jdbcTemplate.update("SET REFERENTIAL_INTEGRITY FALSE");

        for (String tableName : tableNames) {
            jdbcTemplate.update("TRUNCATE TABLE " + tableName);
            jdbcTemplate.update("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1");
        }

        jdbcTemplate.update("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @Transactional
    public void insertInitialData() {
        jdbcTemplate.update(
                "INSERT INTO theme(name, desc, price) VALUES (?, ?, ?)",
                "테마이름", "테마설명", 22_000);

        jdbcTemplate.update(
                "INSERT INTO schedule (theme_id, date, time) VALUES (?, ?, ?)",
                1L, Date.valueOf("2022-08-11"), Time.valueOf("13:00:00"));

        jdbcTemplate.update(
                "INSERT INTO member (id, username, password, name, phone) VALUES (?, ?, ?, ?, ?)",
                0L, "admin", "password", "admin", "010-1234-5678");

        jdbcTemplate.update(
                "INSERT INTO member (username, password, name, phone) VALUES (?, ?, ?, ?)",
                "username", "password", "name", "010-1234-5678");

        jdbcTemplate.update(
                "INSERT INTO reservation (schedule_id, member_id, name) VALUES (?, ?, ?)",
                1L, 1L, "name");
    }
}

