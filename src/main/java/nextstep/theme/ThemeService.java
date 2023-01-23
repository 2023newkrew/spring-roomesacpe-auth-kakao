package nextstep.theme;

import nextstep.schedule.ScheduleDao;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.naming.NotContextException;
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
        if (themeDao.findByNameAndPrice(themeRequest)) {
            throw new KeyAlreadyExistsException("Already Registered Theme");
        }
        return themeDao.save(themeRequest.toEntity());
    }

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public void delete(Long id) throws NotContextException {
        Optional<List<Theme>> themeList = themeDao.findById(id);
        if (themeList.get().isEmpty()) {
            throw new NotContextException("Theme not registered");
        }
        if (scheduleDao.isExistsByThemeId(id)) {
            throw new KeyAlreadyExistsException("Exists Registered scheduled");
        }
        themeDao.delete(id);
    }
}
