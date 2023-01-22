package nextstep.theme;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ThemeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ThemeDao themeDao;
    private Theme theme = Theme.builder()
            .name("name")
            .desc("desc")
            .price(22000)
            .build();


    @BeforeEach
    void setUp() {
        themeDao = new ThemeDao(jdbcTemplate);
    }

    @Test
    void 저장후에_아이디를_반환한다() {
        assertThat(themeDao.save(theme)).isInstanceOf(Long.class);
    }

    @Test
    void 존재하지_않는_아이디를_조회하면_빈_값을_반환한다() {
        assertThat(themeDao.findById(1L)).isEqualTo(Optional.empty());
    }

    @Test
    void 아이디로_테마를_조회할_수_있다() {
        Long id = themeDao.save(theme);
        assertThat(themeDao.findById(id).get()).isInstanceOf(Theme.class);
    }

    @Test
    void 이름으로_테마를_조회할_수_있다() {
        themeDao.save(theme);
        assertThat(themeDao.findByName(theme.getName()).get()).isInstanceOf(Theme.class);
    }

    @Test
    void 테마를_삭제할_수_있다() {
        Long id = themeDao.save(theme);
        themeDao.delete(id);
        assertThat(themeDao.findById(id)).isEqualTo(Optional.empty());
    }
}
