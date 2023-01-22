package nextstep.schedule.repository;

import nextstep.schedule.dao.ScheduleDao;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public Long save(Schedule schedule) {

        return scheduleDao.save(ScheduleMapper.INSTANCE.domainToEntity(schedule));
    }

    @Override
    public Optional<Schedule> findById(Long id) {

        return Optional.ofNullable(ScheduleMapper.INSTANCE.entityToDomain(scheduleDao.findById(id)));
    }

    @Override
    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {

        return ScheduleMapper.INSTANCE.entityListToDomainList(scheduleDao.findByThemeIdAndDate(themeId, date));
    }

    @Override
    public int deleteById(Long id) {

        return scheduleDao.deleteById(id);
    }
}
