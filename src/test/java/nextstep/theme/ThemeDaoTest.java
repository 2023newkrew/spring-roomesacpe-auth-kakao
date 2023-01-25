package nextstep.theme;

import static nextstep.Constant.THEME_DESCRIPTION;
import static nextstep.Constant.THEME_NAME;
import static nextstep.Constant.THEME_PRICE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThemeDaoTest {
    @Autowired
    ThemeDao themeDao;

    @Test
    void save() {
        Theme theme = Theme.builder().name(THEME_NAME).desc(THEME_DESCRIPTION).price(THEME_PRICE).build();
        Long id = themeDao.save(theme);
        System.out.println(id);
        assertThat(id).isNotNull();
    }
}
