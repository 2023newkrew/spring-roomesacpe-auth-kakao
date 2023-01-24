package nextstep.theme;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import nextstep.exceptions.exception.duplicate.DuplicatedThemeException;
import nextstep.exceptions.exception.notFound.ThemeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ThemeServiceTest {

    @Mock
    private ThemeDao themeDao;

    @InjectMocks
    private ThemeService themeService;

    private Theme theme = Theme.builder()
            .id(1L)
            .price(1000)
            .desc("desc")
            .name("test")
            .build();

    @Test
    void 중복된_테마를_생성하려고_하면_예외가_발생한다() {
        given(themeDao.findByName(theme.getName())).willReturn(Optional.of(theme));
        assertThatThrownBy(() -> themeService.create(theme))
                .isInstanceOf(DuplicatedThemeException.class);
    }

    @Test
    void 존자하지_않는_테마를_조회하려고_하면_예외가_발생한다() {
        given(themeDao.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> themeService.findById(1L))
                .isInstanceOf(ThemeNotFoundException.class);
    }

    @Test
    void 존재하지_않는_예약을_삭제하려고_하면_예외가_발생한다() {
        given(themeDao.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> themeService.deleteById(1L))
                .isInstanceOf(ThemeNotFoundException.class);
    }

}
