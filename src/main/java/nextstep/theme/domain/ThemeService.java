package nextstep.theme.domain;

import nextstep.support.NotExistEntityException;
import nextstep.theme.dto.ThemeRequest;
import nextstep.theme.persistence.ThemeDao;
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
        Theme theme = themeDao.findById(id).orElseThrow(NotExistEntityException::new);
        if (theme == null) {
            throw new NotExistEntityException();
        }

        themeDao.delete(id);
    }
}
