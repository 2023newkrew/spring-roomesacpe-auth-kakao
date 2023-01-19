package nextstep.theme.service;

import java.util.List;
import nextstep.exception.NotFoundException;
import nextstep.theme.Theme;
import nextstep.theme.dto.ThemeRequest;
import nextstep.theme.repository.ThemeDao;
import org.springframework.stereotype.Service;

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
        Theme theme = themeDao.findById(id);
        if (theme == null) {
            throw new NotFoundException();
        }

        themeDao.delete(id);
    }
}
