package nextstep.theme;

import static nextstep.common.exception.ExceptionMessage.INVALID_THEME_ID;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.common.exception.NotExistEntityException;
import nextstep.theme.dto.ThemeRequestDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeDao themeDao;

    public Long create(ThemeRequestDto themeRequestDto) {
        return themeDao.save(themeRequestDto.toEntity());
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
