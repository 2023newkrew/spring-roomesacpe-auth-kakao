package nextstep.support;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    }
}

