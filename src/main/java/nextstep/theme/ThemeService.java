package nextstep.theme;

import nextstep.exceptions.exception.duplicate.DuplicatedThemeException;
import nextstep.exceptions.exception.notFound.ThemeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(Theme theme) {
        themeDao.findByName(theme.getName()).ifPresent((existTheme) -> {
            throw new DuplicatedThemeException();
        });
        return themeDao.save(theme);
    }

    public Theme findById(Long id) {
        return themeDao.findById(id).orElseThrow(ThemeNotFoundException::new);
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public void delete(Long id) {
        themeDao.findById(id).orElseThrow(ThemeNotFoundException::new);
        themeDao.delete(id);
    }
}
