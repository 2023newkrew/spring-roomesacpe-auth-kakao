package nextstep.theme;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@JdbcTest
@Transactional
@SpringBootTest
public class ThemeDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

     @Test
    void save() {
        ThemeDao themeDao = new ThemeDao(jdbcTemplate);
         themeDao.save(new Theme("테마 이름100", "테마 설명", 22_000));
         themeDao.save(new Theme("테마 이름200", "테마 설명", 22_000));
         themeDao.save(new Theme("테마 이름300", "테마 설명", 22_000));

         Long id = themeDao.save(new Theme("테마 이름400", "테마 설명", 22_000));

        List<Theme> themes = themeDao.findAll();

        for(Theme theme : themes) {
            System.out.println(theme.getId() + " " + theme.getName());
        }
        assertThat(id).isEqualTo(4);
    }
}
