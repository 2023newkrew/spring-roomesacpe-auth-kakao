package nextstep.theme;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static nextstep.Constant.THEME_DESCRIPTION;
import static nextstep.Constant.THEME_NAME;
import static nextstep.Constant.THEME_PRICE;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ThemeDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void save() {
        ThemeDao themeDao = new ThemeDao(jdbcTemplate);
        Theme theme = Theme.builder().name(THEME_NAME).desc(THEME_DESCRIPTION).price(THEME_PRICE).build();
        Long id = themeDao.save(theme);
        assertThat(id).isNotNull();
    }
}
