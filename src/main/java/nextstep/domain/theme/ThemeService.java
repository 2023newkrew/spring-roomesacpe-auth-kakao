package nextstep.domain.theme;

import nextstep.dto.theme.ThemeRequest;
import nextstep.persistence.theme.Theme;
import nextstep.persistence.theme.ThemeDao;
import nextstep.support.NotExistEntityException;
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
        themeDao.findById(id).orElseThrow(NotExistEntityException::new);

        themeDao.delete(id);
    }
}
