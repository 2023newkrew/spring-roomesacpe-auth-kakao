package nextstep.service;

import nextstep.error.ApplicationException;
import nextstep.domain.theme.Theme;
import nextstep.domain.theme.ThemeDao;
import nextstep.dto.request.ThemeRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static nextstep.error.ErrorType.THEME_NOT_FOUND;

@Service
public class ThemeService {
    private ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    @Transactional
    public Long create(ThemeRequest themeRequest) {
        return themeDao.save(themeRequest.toEntity());
    }

    @Transactional(readOnly = true)
    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    @Transactional
    public void delete(Long id) {
        checkExistsByThemeId(id);
        themeDao.delete(id);
    }

    @Transactional(readOnly = true)
    public void checkExistsByThemeId(Long themeId) {
        Theme theme = themeDao.findById(themeId);
        if (theme == null) {
            throw new ApplicationException(THEME_NOT_FOUND);
        }
    }
}
