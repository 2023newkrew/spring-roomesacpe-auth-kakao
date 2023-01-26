package nextstep.service;

import java.util.List;
import nextstep.dto.theme.ThemeRequest;
import nextstep.entity.Theme;
import nextstep.exception.NotFoundException;
import nextstep.repository.ThemeDao;
import org.springframework.stereotype.Service;

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
            throw new NotFoundException();
        }

        themeDao.delete(id);
    }
}
