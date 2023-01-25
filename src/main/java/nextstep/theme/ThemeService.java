package nextstep.theme;

import com.sun.jdi.request.DuplicateRequestException;
import nextstep.schedule.ScheduleDao;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.naming.NotContextException;
import java.util.List;
import java.util.Optional;

import static nextstep.config.Messages.*;

@Service
public class ThemeService {
    private final ThemeDao themeDao;
    private final ScheduleDao scheduleDao;

    public ThemeService(ThemeDao themeDao, ScheduleDao scheduleDao) {
        this.themeDao = themeDao;
        this.scheduleDao = scheduleDao;
    }

    public Long create(ThemeRequest themeRequest) {
        if (themeDao.findByNameAndPrice(themeRequest)) {
            throw new DuplicateRequestException(ALREADY_REGISTERED_THEME.getMessage());
        }
        return themeDao.save(themeRequest.toEntity());
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public void delete(Long id) throws NotContextException {
        Optional<List<Theme>> themeList = themeDao.findById(id);
        if (themeList.isEmpty() || themeList.get().isEmpty()) {
            throw new NotContextException(THEME_NOT_FOUND.getMessage());
        }
        if (scheduleDao.isExistsByThemeId(id)) {
            throw new KeyAlreadyExistsException(ALREADY_REGISTERED_SCHEDULE.getMessage());
        }
        themeDao.delete(id);
    }
}
