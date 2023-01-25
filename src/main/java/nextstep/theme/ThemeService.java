package nextstep.theme;

import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(ThemeRequest themeRequest) {
        return themeDao.save(themeRequest.toEntity());
    }

    public List<ThemeResponse> findAll() {
        return themeDao.findAll().stream().map(e->e.toResponse()).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Theme theme = themeDao.findById(id);
        if (theme == null) {
            throw new NotExistEntityException();
        }

        themeDao.delete(id);
    }
}
