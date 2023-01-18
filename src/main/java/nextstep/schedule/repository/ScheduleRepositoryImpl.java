package nextstep.schedule.repository;

import nextstep.schedule.dao.ScheduleDao;
import nextstep.schedule.entity.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleDao scheduleDao;

    @Autowired
    public ScheduleRepositoryImpl(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @Override
    public Long save(ScheduleEntity scheduleEntity) {

        return scheduleDao.save(scheduleEntity);
    }

    @Override
    public Optional<ScheduleEntity> findById(Long id) {

        return Optional.ofNullable(scheduleDao.findById(id));
    }

    @Override
    public List<ScheduleEntity> findByThemeIdAndDate(Long themeId, String date) {

        return Optional.ofNullable(scheduleDao.findByThemeIdAndDate(themeId, date))
                .orElse(new ArrayList<>())
                ;
    }

    @Override
    public boolean existsById(Long id) {

        return scheduleDao.existsById(id);
    }

    @Override
    public int deleteById(Long id) {

        return scheduleDao.deleteById(id);
    }
}
