package nextstep.theme;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:test")
public class ThemeDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void save() {
        ThemeDao themeDao = new ThemeDao(jdbcTemplate);
        Long id = themeDao.save(new Theme("테마 이름", "테마 설명", 22_000));
        assertThat(id).isNotNull();
    }
}
