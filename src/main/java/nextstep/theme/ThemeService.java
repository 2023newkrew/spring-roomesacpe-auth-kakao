package nextstep.theme;

import nextstep.support.exception.RoomEscapeExceptionCode;
import nextstep.support.exception.ThemeException;
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
            throw new ThemeException(RoomEscapeExceptionCode.NOT_FOUND_THEME);
        }

        themeDao.delete(id);
    }
}
