package nextstep.theme;

import lombok.RequiredArgsConstructor;
import nextstep.exception.NotExistEntityException;
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
        Theme theme = themeDao.findById(id);
        if (theme == null) {
            throw new NotExistEntityException("테마");
        }
        themeDao.delete(id);
    }
}
