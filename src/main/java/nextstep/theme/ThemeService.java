package nextstep.theme;

import nextstep.schedule.ScheduleDao;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    private final ThemeDao themeDao;
    private final ScheduleDao scheduleDao;

    public ThemeService(ThemeDao themeDao, ScheduleDao scheduleDao) {
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
    }

    public Long create(ThemeRequest themeRequest) {
        // 이름/가격

        return themeDao.save(themeRequest.toEntity());
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public void delete(Long id) {
        Optional<List<Theme>> themeList = themeDao.findById(id);
        if (themeList.get().isEmpty()) {
            throw new NotExistEntityException("테마가 등록되어 있지 않음");
        }
        if (scheduleDao.isExistsByThemeId(id)) {
            throw new NotExistEntityException("등록된 scheduled 존재함");
        }
        themeDao.delete(id);
    }
}
