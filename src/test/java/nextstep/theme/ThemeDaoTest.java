package nextstep.theme;

import nextstep.theme.domain.Theme;
import nextstep.theme.persistence.ThemeDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ThemeDaoTest {
    public static final String THEME_NAME = "테마 이름";
    public static final String THEME_DESC = "테마 설명";
    public static final int THEME_PRICE = 22_000;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void save() {
        ThemeDao themeDao = new ThemeDao(jdbcTemplate);
        Long id = themeDao.save(new Theme(THEME_NAME, THEME_DESC, THEME_PRICE));
        assertThat(id).isNotNull();
    }
}
