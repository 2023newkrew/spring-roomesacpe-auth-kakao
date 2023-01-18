package nextstep.theme;

import nextstep.support.ThemeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private final ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Long create(ThemeRequest themeRequest) {
        return themeDao.save(themeRequest.toEntity());
    }

    public List<ThemeResponse> findAll() {
        return themeDao.findAll()
                .stream()
                .map(theme -> ThemeResponse.of(theme))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Theme theme = themeDao.findById(id).orElseThrow(ThemeNotFoundException::new);
        themeDao.delete(id);
    }
}
