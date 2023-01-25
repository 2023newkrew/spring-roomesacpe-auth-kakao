package nextstep.theme;

import java.util.List;
import nextstep.exception.CustomException;
import nextstep.exception.ErrorCode;
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
            throw new CustomException(ErrorCode.NO_SUCH_ENTITY);
        }

        themeDao.delete(id);
    }
}
