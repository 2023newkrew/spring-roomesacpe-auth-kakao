package nextstep.theme;

import nextstep.support.exception.InvalidThemeException;
import nextstep.support.exception.NotExistEntityException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(ThemeRequest themeRequest) throws InvalidThemeException {
        if (!validateTheme(themeRequest)) {
            throw new InvalidThemeException();
        }

        return themeDao.save(themeRequest.toEntity());
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public void delete(Long id) {
        Theme theme = themeDao.findById(id);
        if (theme == null) {
            throw new NotExistEntityException();
        }

        themeDao.delete(id);
    }

    private Boolean validateTheme(ThemeRequest themeRequest) {
        if (themeRequest.getName().length() > 20 || themeRequest.getDesc().length() > 255 || themeRequest.getPrice() < 0) {
            return false;
        }

        return true;
    }
}
