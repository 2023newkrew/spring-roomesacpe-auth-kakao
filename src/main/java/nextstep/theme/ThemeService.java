package nextstep.theme;

import nextstep.error.ApplicationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static nextstep.error.ErrorType.THEME_NOT_FOUND;

@Service
public class ThemeService {
    private ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(ThemeRequest themeRequest) {
        return themeDao.save(themeRequest.toEntity());
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public void delete(Long id) {
        checkExistsByThemeId(id);
        themeDao.delete(id);
    }

    public void checkExistsByThemeId(Long themeId) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new ApplicationException(THEME_NOT_FOUND);
        }
    }
}
