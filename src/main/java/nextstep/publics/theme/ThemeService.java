package nextstep.publics.theme;

import nextstep.framework.exception.BusinessException;
import nextstep.framework.exception.CommonErrorCode;
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
            throw new BusinessException(CommonErrorCode.NOT_EXIST_ENTITY);
        }

        themeDao.delete(id);
    }
}
