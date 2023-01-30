package nextstep.domain.theme;

import nextstep.interfaces.theme.dto.ThemeRequest;
import nextstep.interfaces.exception.NotExistEntityException;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new NotExistEntityException("해당 테마가 존재하지 않습니다.");
        }

        themeDao.delete(id);
    }
}
