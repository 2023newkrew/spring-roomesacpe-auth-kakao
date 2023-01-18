package nextstep.theme;

import static nextstep.common.exception.ExceptionMessage.INVALID_THEME_ID;

import lombok.RequiredArgsConstructor;
import nextstep.common.exception.NotExistEntityException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeDao themeDao;

    public Long create(ThemeRequest themeRequest) {
        return themeDao.save(themeRequest.toEntity());
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public void delete(Long id) {
        themeDao.findById(id)
            .orElseThrow(() -> new NotExistEntityException(INVALID_THEME_ID.getMessage()));
        themeDao.delete(id);
    }
}
