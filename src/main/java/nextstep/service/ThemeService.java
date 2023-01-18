package nextstep.service;

import nextstep.dao.ThemeDao;
import nextstep.domain.Theme;
import nextstep.support.exception.NotExistEntityException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(Theme theme) {
        return themeDao.save(theme);
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public void delete(long id) {
        Theme theme = themeDao.findById(id);
        if (theme == null) {
            throw new NotExistEntityException();
        }

        themeDao.delete(id);
    }
}
