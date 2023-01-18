package nextstep.service;

import nextstep.infra.exception.api.NoSuchThemeException;
import nextstep.domain.domain.Theme;
import nextstep.repository.ThemeDao;
import nextstep.domain.model.request.ThemeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeDao themeDao;

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
        Theme theme = themeDao.findById(id);
        if (theme == null) {
            throw new NoSuchThemeException();
        }

        themeDao.delete(id);
    }
}
